package com.example.tinylibrary.web.rest;

import com.example.tinylibrary.domain.Book;
import com.example.tinylibrary.domain.Record;
import com.example.tinylibrary.domain.User;
import com.example.tinylibrary.service.BookService;
import com.example.tinylibrary.service.RecordService;
import com.example.tinylibrary.service.UserService;
import com.example.tinylibrary.util.Constant;
import com.example.tinylibrary.util.DateUtil;
import com.example.tinylibrary.web.exception.CustomTinyLibraryException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class RecordResource {

  @Value("${max.duration}")
  private int duration;

  @Value("${max.book}")
  private int count;

  @Autowired private RecordService recordService;

  @Autowired private UserService userService;

  @Autowired private BookService bookService;

  /**
   * POST Add a new borrow book
   *
   * @param record to create
   * @return the ResponseEntity with status 201 (Created) and with body the new record
   */
  @PostMapping(path = "/borrow", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "POST Add a new borrow book record", value = "borrowBook")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = Record.class),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Record> borrowBook(@Valid @RequestBody Record record)
      throws URISyntaxException {
    log.info("REST request to add borrow book record : {}", record.toString());
    record.setBorrowDate(new Date());
    record.setExpiryDate(DateUtil.setExpiryDate(duration));
    User user = userService.searchUser(record.getUserId());
    if (null == user) {
      throw new CustomTinyLibraryException("User Not Found!");
    }
    Book book = bookService.findBookById(record.getBookId());
    if (null == book) {
      throw new CustomTinyLibraryException("Book not Found!");
    }
    Record result = recordService.saveRecord(record, count, user, book, Constant.ACTION_BORROW);
    return ResponseEntity.created(new URI("/api/books/borrow" + result.getId())).body(result);
  }

  /**
   * POST Update a return book record
   *
   * @param record to update
   * @return the ResponseEntity with status 200
   */
  @PostMapping(path = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "POST Update return record", value = "returnBook")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = Record.class),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Record> returnBook(@Valid @RequestBody Record record) {
    log.info("REST request to update borrow book record : {}", record.toString());
    record.setReturnDate(new Date());
    User user = userService.searchUser(record.getUserId());
    if (null == user) {
      throw new CustomTinyLibraryException("User Not Found!");
    }
    Book book = bookService.findBookById(record.getBookId());
    if (null == book) {
      throw new CustomTinyLibraryException("Book not Found!");
    }
    List<Record> records = recordService.findAll(record.getUserId());
    recordService.checkBookUnderUser(records, record.getBookId());
    Record result = recordService.saveRecord(record, count, user, book, Constant.ACTION_RETURN);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
