package com.accio.LibraryManagementSystem.Models;

import com.accio.LibraryManagementSystem.Enum.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class Book
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookID;

    private String bookName;
    private Integer bookPages;
    private Boolean isIssued;
    //private String authorName; as author is connected so fetch from it -- book.getAuthor().getName();

    @Enumerated(value = EnumType.STRING)//consider as String said here
    private Genre genre;//can have only one of 6 values

    @JoinColumn
    @ManyToOne
    private Author author;
}
