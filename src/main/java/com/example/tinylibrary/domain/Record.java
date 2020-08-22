package com.example.tinylibrary.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "record")
public class Record implements Serializable {

    private static final long serialVersionUID = 123456789L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User Id is mandatory!")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Book Id is mandatory!")
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "borrow_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date borrowDate;

    @Column(name = "expiry_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Column(name = "return_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
}
