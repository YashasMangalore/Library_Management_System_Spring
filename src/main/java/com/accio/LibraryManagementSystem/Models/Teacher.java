package com.accio.LibraryManagementSystem.Models;

import com.accio.LibraryManagementSystem.Enum.Branch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;
    @Enumerated(value = EnumType.STRING)
    private Branch branch;
    @Column(unique = true,length = 100)
    private String email;
    private Integer age;
    private String name;
    private String address;
    private Integer noOfStudents;
}
