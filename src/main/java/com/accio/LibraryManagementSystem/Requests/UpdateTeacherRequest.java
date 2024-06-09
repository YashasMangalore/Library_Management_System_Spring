package com.accio.LibraryManagementSystem.Requests;

import com.accio.LibraryManagementSystem.Enum.Branch;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UpdateTeacherRequest
{
    private Branch branch;
    private String email;
    private Integer age;
    private String name;
    private String address;
    private Integer noOfStudents;
}
