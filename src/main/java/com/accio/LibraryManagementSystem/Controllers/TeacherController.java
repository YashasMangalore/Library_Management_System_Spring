package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Requests.UpdateStudentRequest;
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

    @GetMapping("all-teachers")
    public ResponseEntity<List<Teacher>> findAllTeachers()
    {
        List<Teacher> ansList=teacherService.findAllTeachers();
        return new ResponseEntity<>(ansList, HttpStatus.OK);
    }

    @GetMapping("all-students")
    public ResponseEntity<List<Student>> findAllStudents(@RequestParam String teacherName)
    {
        List<Student> ansList=teacherService.findAllStudents(teacherName);
        return new ResponseEntity<>(ansList, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateTeacher(@RequestParam Integer teacherId, @RequestBody UpdateTeacherRequest teacherRequest)
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
    public ResponseEntity<String> deleteTeacher(@RequestParam String teacherName,@RequestParam Integer studentId)
    {
        String result=teacherService.deleteTeacher(teacherName,studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
/*

    @GetMapping("/get-students-by-teacher-name/{teacher}")
    public ResponseEntity<List<String>> getStudentsByTeacherName(@PathVariable String teacher){
        List<String> students = studentService.findStudentsFromTeacher(teacher); // Assign list of student by calling service layer method
        return new ResponseEntity<>(students, HttpStatus.CREATED);
    }
 */