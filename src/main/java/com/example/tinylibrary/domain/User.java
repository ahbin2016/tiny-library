package com.example.tinylibrary.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Data
@Table(name = "user")
public class User implements Serializable {

  private static final long serialVersionUID = 12345L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "User Name is mandatory!")
  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "count_book")
  private int countBook;
}
