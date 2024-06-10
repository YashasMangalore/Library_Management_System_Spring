package com.accio.LibraryManagementSystem.Responses;

import com.accio.LibraryManagementSystem.Enum.Branch;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse
{
    private String name;
    private Integer age;
    private Branch branch;
    private String email;
    private Integer noOfStudents;
}
