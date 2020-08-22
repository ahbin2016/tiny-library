package com.example.tinylibrary.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "book")
public class Book implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Book ISBN is mandatory!")
  @Column(name = "isbn", nullable = false)
  private String isbn;

  @NotBlank(message = "Book Name is mandatory!")
  @Column(name = "name", nullable = false)
  private String name;

  @NotBlank(message = "Book Author is mandatory!")
  @Column(name = "author", nullable = false)
  private String author;

  @NotBlank(message = "Book Summary is mandatory!")
  @Column(name = "summary", nullable = false)
  private String summary;

  @Column(name = "publish_date", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date publishDate;

  @Column(name = "available")
  private Boolean available;
}
