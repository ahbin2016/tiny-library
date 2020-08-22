package com.example.tinylibrary.web.rest;

import com.example.tinylibrary.domain.Book;
import com.example.tinylibrary.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookResource {

  @Autowired private BookService bookService;

  /**
   * POST Add a new book
   * @param book to create
   * @return the ResponseEntity with status 201 (Created) and with body the new book
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "POST Add a new book", value = "addBook")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = Book.class),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) throws URISyntaxException {
    log.info("REST request to add Book : {}", book.toString());
    book.setAvailable(true);
    Book result = bookService.saveBook(book);
    return ResponseEntity.created(new URI("/api/books/" + result.getId())).body(result);
  }

  /**
   * PUT Updates an existing book
   *
   * @param book the country to update
   * @return the ResponseEntity with status 200 (OK) and with body the updated book or with status
   *     500 (Internal Server Error) if the book could not be be updated
   * @throws URISyntaxException if the Location URI syntax is incorrect
   */
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "PUT Updates an existing book", value = "updateBook")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = Book.class),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) {
    log.info("REST request to update book : {}", book.toString());
    Book results = bookService.updateBook(book);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }

  /**
   * DELETE /books/:id : DELETE the "id" book
   *
   * @param id the id of the book to delete
   * @return the ResponseEntity with status 200 (OK)
   */
  @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "DELETE book by identifier", value = "deleteBook")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
    log.info("REST request to delete Book : {}", id);
    bookService.deleteBook(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * GET all books by given request parameter
   *
   * @return the ResponseEntity with status 200 and list of books in body
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "Search all books", value = "searchBooks")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = List.class),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<List<Book>> searchBooks(
      @RequestParam(value = "isbn", required = false) String isbn,
      @RequestParam(value = "name", required = false) String name) {
    log.info("REST request to search books");
    List<Book> results = bookService.searchBooks(isbn, name);
    return new ResponseEntity<>(results, HttpStatus.OK);
  }
}
