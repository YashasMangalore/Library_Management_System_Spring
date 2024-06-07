package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public String deleteStudent(String studentName,Integer studentId)
    {
        Student student=studentRepository.findById(studentId).orElseThrow(() -> new NoSuchElementException("Teacher not found with ID: " + studentId));
        studentRepository.delete(student);
        return "Student with student-id: "+studentId+" has been removed from the database";
    }
}
