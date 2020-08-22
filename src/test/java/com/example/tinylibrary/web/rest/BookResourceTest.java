package com.example.tinylibrary.web.rest;

import com.example.tinylibrary.TinyLibraryApplication;
import com.example.tinylibrary.domain.Book;
import com.example.tinylibrary.service.BookService;
import com.example.tinylibrary.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TinyLibraryApplication.class)
public class BookResourceTest {

  private MockMvc mvc;

  @Mock
  private BookService bookService;

  @Autowired private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    BookResource bookResource = new BookResource();
    ReflectionTestUtils.setField(bookResource, "bookService", bookService);
    this.mvc = MockMvcBuilders.standaloneSetup(bookResource).build();
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

  @Test
  public void testAddBook() throws Exception {
    when(bookService.saveBook(mockBook())).thenReturn(mockBook());
    mvc.perform(
            post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mockBook())))
        .andExpect(status().isCreated());
  }
}
