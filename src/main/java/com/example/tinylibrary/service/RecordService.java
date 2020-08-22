package com.example.tinylibrary.service;

import com.example.tinylibrary.domain.Book;
import com.example.tinylibrary.domain.Record;
import com.example.tinylibrary.domain.User;
import com.example.tinylibrary.repository.RecordRepository;
import com.example.tinylibrary.util.Constant;
import com.example.tinylibrary.web.exception.CustomTinyLibraryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class RecordService {

  @Autowired private RecordRepository recordRepository;

  @Autowired private UserService userService;

  @Autowired private BookService bookService;

  public Record saveRecord(Record record, int count, User user, Book book, String action) {

    if (action.equalsIgnoreCase(Constant.ACTION_BORROW)) {
      log.info("validating user for the count of book record");
      validateUser(user, count);
      log.info("checking book availability");
      checkBookAvailability(book);
    }
    log.info("Calling service to add borrow book record");
    Record saveRecord = recordRepository.save(record);

    log.info("Calling service to update book count borrowed");
    updateBookCount(user, action);
    log.info("Calling service to update book availability");
    updateBookAvailability(book, action);
    return saveRecord;
  }

  private void updateBookAvailability(Book book, String action) {
    book.setAvailable(!action.equalsIgnoreCase(Constant.ACTION_BORROW));
    bookService.updateBook(book);
  }

  private void checkBookAvailability(Book book) {
    if (!book.getAvailable()) {
      throw new CustomTinyLibraryException("Book currently not yet returned!");
    }
  }

  private void updateBookCount(User user, String action) {
    if (action.equalsIgnoreCase(Constant.ACTION_BORROW)) {
      user.setCountBook(user.getCountBook() + 1);
    } else {
      user.setCountBook(user.getCountBook() - 1);
    }
    userService.updateUser(user);
  }

  private void validateUser(User user, int count) {
    if (user.getCountBook() >= count) {
      throw new CustomTinyLibraryException("Maximum borrowed book reached!");
    }
  }
}
