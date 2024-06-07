package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("teacher")
public class TeacherController
{
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/add-teacher")
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

    @DeleteMapping("remove")
    public ResponseEntity<String> deleteTeacher(@RequestParam String teacherName,@RequestParam Integer studentId)
    {
        String result=teacherService.deleteTeacher(teacherName,studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
/*
@PutMapping("/add-student-teacher-pair")
    public ResponseEntity<String> addStudentTeacherPair(@RequestParam String student, @RequestParam String teacher){
        studentService.createStudentTeacherPair(student,teacher);
        return new ResponseEntity<>("New student-teacher pair added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-student-by-name/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable String name){
        Student student =studentService.findStudent(name); // Assign student by calling service layer method
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @GetMapping("/get-teacher-by-name/{name}")
    public ResponseEntity<Teacher> getTeacherByName(@PathVariable String name){
        Teacher teacher = studentService.findTeacher(name); // Assign student by calling service layer method
        return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @GetMapping("/get-students-by-teacher-name/{teacher}")
    public ResponseEntity<List<String>> getStudentsByTeacherName(@PathVariable String teacher){
        List<String> students = studentService.findStudentsFromTeacher(teacher); // Assign list of student by calling service layer method
        return new ResponseEntity<>(students, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity<List<String>> getAllStudents(){
        List<String> students = studentService.findAllStudents(); // Assign list of student by calling service layer method
        return new ResponseEntity<>(students, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-teacher-by-name")
    public ResponseEntity<String> deleteTeacherByName(@RequestParam String teacher){
        studentService.deleteTeacher(teacher);
        return new ResponseEntity<>(teacher + " removed successfully", HttpStatus.CREATED);
    }
    @DeleteMapping("/delete-all-teachers")
    public ResponseEntity<String> deleteAllTeachers(){
        studentService.deleteAllTeachers();
        return new ResponseEntity<>("All teachers deleted successfully", HttpStatus.CREATED);
    }
 */