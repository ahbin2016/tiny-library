package com.example.tinylibrary.service;

import com.example.tinylibrary.domain.Book;
import com.example.tinylibrary.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class BookService {

  @Autowired private BookRepository bookRepository;

  public Book saveBook(Book book) {
    log.info("Calling service to add book");
    return bookRepository.save(book);
  }

  public List<Book> findAllBooks() {
    log.info("Calling service to fetch all book");
    return bookRepository.findAll();
  }

  public Book updateBook(Book book) {
    log.info("Calling service to update book");
    return bookRepository.save(book);
  }

  public void deleteBook(Long id) {
    log.info("Calling service to delete book");
    bookRepository.deleteById(id);
  }

  public List<Book> searchBooks(String isbn, String name) {
    log.info("Calling service to delete book");
    return bookRepository.searchBook(isbn, name);
  }

  public Book findBookById(Long bookId) {
    log.info("Calling service to search book by id");
    Optional<Book> book = bookRepository.findById(bookId);
    return book.orElse(null);
  }
}
