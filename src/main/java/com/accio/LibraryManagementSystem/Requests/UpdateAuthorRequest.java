package com.accio.LibraryManagementSystem.Requests;

import lombok.Data;

@Data
public class UpdateAuthorRequest
{
    private Integer authorID;

    private String authorName;
    private Integer age;
    private Integer noOfBooks;
    private String email;
    private Double ratings;
}
