package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Requests.UpdateTeacherRequest;
import com.accio.LibraryManagementSystem.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController
{
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/add")
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher)
    {
        String response=teacherService.addTeacher(teacher);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all-teachers")
    public ResponseEntity<List<Teacher>> findAllTeachers()
    {
        List<Teacher> ansList=teacherService.findAllTeachers();
        return new ResponseEntity<>(ansList, HttpStatus.OK);
    }

    @GetMapping("/all-students-details")
    public ResponseEntity<List<Student>> findAllStudentsDetails(@RequestParam Integer teacherId,@RequestParam String teacherName)throws Exception
    {
        try
        {
            List<Student> ansList = teacherService.findAllStudentsDetails(teacherId, teacherName);
            return new ResponseEntity<>(ansList, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all-students")
    public ResponseEntity<List<String>> findAllStudents(@RequestParam Integer teacherId, @RequestParam String teacherName)throws Exception
    {
        try
        {
            List<String> ansList = teacherService.findAllStudents(teacherId, teacherName);
            return new ResponseEntity<>(ansList, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeacher(@RequestParam Integer teacherId, @RequestBody UpdateTeacherRequest teacherRequest) throws Exception
    {
        try
        {
            String response=teacherService.updateTeacher(teacherId,teacherRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteTeacher(@RequestParam String teacherName,@RequestParam Integer studentId)throws Exception
    {
        try
        {
            String result = teacherService.deleteTeacher(teacherName, studentId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}