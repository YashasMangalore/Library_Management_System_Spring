package com.accio.LibraryManagementSystem.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse
{
    private String authorName;
    private Integer age;
    private Integer noOfBooks;
    private Double ratings;
}
