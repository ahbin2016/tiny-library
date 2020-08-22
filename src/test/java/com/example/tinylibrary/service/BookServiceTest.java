package com.example.tinylibrary.service;

import com.example.tinylibrary.domain.Book;
import com.example.tinylibrary.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookServiceTest {

  BookService bookService;

  @MockBean BookRepository bookRepository;

  @Before
  public void setUp() {
    bookService = new BookService();
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(bookService, "bookRepository", bookRepository);
  }

  @Test
  public void testSaveBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(mockBook());
    Book book = bookService.saveBook(mockBook());
    assertNotNull(book);
  }

  @Test
  public void testUpdateBook() {
    when(bookRepository.save(any(Book.class))).thenReturn(mockBook());
    Book book = bookService.updateBook(mockBook());
    assertNotNull(book);
  }

  @Test
  public void testSearchBook() {
    String isbn = "TestIsbn";
    String name = "TestName";
    when(bookRepository.searchBook(isbn, name)).thenReturn(Collections.singletonList(mockBook()));
    List<Book> book = bookService.searchBooks(isbn, name);
    assertNotNull(book);
  }

  private Book mockBook() {
    Book book = new Book();
    book.setId(1L);
    book.setAvailable(true);
    book.setIsbn("TestISBN");
    book.setAuthor("TestAuthor");
    book.setName("TestName");
    book.setSummary("TestSummary");
    return book;
  }
}
