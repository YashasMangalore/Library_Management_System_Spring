package com.accio.LibraryManagementSystem.Requests;

import lombok.Data;

@Data
public class UpdateBookRequest
{
    private String bookName;
    private Integer bookPages;
    private Boolean isIssued;
}
