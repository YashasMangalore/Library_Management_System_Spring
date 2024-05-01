package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{
    @Autowired
    private StudentRepository studentRepository;

    public String addStudent(Student student)
    {
        studentRepository.save(student);
        return "Student saved successfully";
    }

    public List<Student> findAllStudents()
    {
        return studentRepository.findAll();
    }
}
