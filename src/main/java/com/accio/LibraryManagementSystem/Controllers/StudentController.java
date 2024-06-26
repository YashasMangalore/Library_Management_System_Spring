package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Requests.UpdateStudentRequest;
import com.accio.LibraryManagementSystem.Responses.TeacherResponse;
import com.accio.LibraryManagementSystem.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/api/v1/student")
@RestController
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student)
    {
        String result=studentService.addStudent(student);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/all-students")
    public ResponseEntity<List<Student>> findAllStudents()
    {
        List<Student> ansList=studentService.findAllStudents();
        return new ResponseEntity<>(ansList, HttpStatus.OK);
    }

    @GetMapping("/teacher")
    public ResponseEntity<TeacherResponse> findTeacher(@RequestParam Integer studentId,@RequestParam String studentName) throws Exception
    {
        try
        {
            TeacherResponse teacherResponse = studentService.findTeacher(studentId, studentName);
            return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestParam Integer studentId, @RequestBody UpdateStudentRequest studentRequest)throws Exception
    {
        try
        {
            String response=studentService.updateStudent(studentId,studentRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/associate-student-teacher")
    public ResponseEntity<String> associateStudentAndTeacher(@RequestParam("studentID")Integer studentID,
                                                         @RequestParam("teacherID")Integer teacherID)throws Exception
    {
        try
        {
            String response=studentService.associateStudentAndTeacher(studentID,teacherID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteStudent(@RequestParam String studentName,@RequestParam Integer studentId)throws Exception
    {
        try
        {
            String result = studentService.deleteStudent(studentName, studentId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
