package com.accio.LibraryManagementSystem.Requests;

import lombok.Data;

@Data
public class UpdateStudentRequest
{
    private String email;
    private String name;
    private Integer age;
    private String branch;
    private String address;
    private String teacherName;
}
