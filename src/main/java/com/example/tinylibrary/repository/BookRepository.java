package com.example.tinylibrary.repository;

import com.example.tinylibrary.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  @Query(
      "SELECT b FROM Book b WHERE (:isbn is null or b.isbn = :isbn) and (:name is null"
          + " or b.name = :name)")
  List<Book> searchBook(@Param("isbn") String isbn, @Param("name") String name);
}
