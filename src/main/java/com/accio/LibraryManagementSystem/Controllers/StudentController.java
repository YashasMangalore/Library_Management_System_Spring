package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("student")
@RestController
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @PostMapping("add")
    public ResponseEntity<String> addStudent(@RequestBody Student student)
    {
        String result=studentService.addStudent(student);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("all-students")
    public ResponseEntity<List<Student>> findAllStudents()
    {
        List<Student> ansList=studentService.findAllStudents();
        return new ResponseEntity<>(ansList, HttpStatus.OK);
    }

    @DeleteMapping("remove")
    public ResponseEntity<String> deleteStudent(@RequestParam String studentName,@RequestParam Integer studentId)
    {
        String result=studentService.deleteStudent(studentName,studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
