package com.accio.LibraryManagementSystem.Models;

import com.accio.LibraryManagementSystem.Enum.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="author_info")
public class Author
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorID;
    @Column(unique = true)
    private String authorName;

    private Integer age;
    private Integer noOfBooks;
    private String email;
    private Double ratings;
}
