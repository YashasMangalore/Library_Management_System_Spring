package com.accio.LibraryManagementSystem.Services;

import ch.qos.logback.core.joran.conditional.ThenAction;
import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeacherService
{
    @Autowired
    private TeacherRepository teacherRepository;

    public String addTeacher(Teacher newTeacher)
    {
        Teacher teacher=teacherRepository.save(newTeacher);
        return "Teacher has been saved successfully with teacher-id: "+teacher.getTeacherId();
    }

    public List<Teacher> findAllTeachers()
    {
        return teacherRepository.findAll();
    }

    public String deleteTeacher(String teacherName, Integer teacherId)
    {
        Teacher teacher=teacherRepository.findById(teacherId).orElseThrow(() -> new NoSuchElementException("Teacher not found with ID: " + teacherId));
        teacherRepository.delete(teacher);
        return "Teacher with teacher-id: "+teacherId+" has been removed from the database";
    }
}

/*
package com.driver;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    public void createStudentTeacherPair(String student, String teacher){
        studentRepository.saveStudentTeacherPair(student, teacher);
    }

    public Student findStudent(String studentName){
        return studentRepository.findStudent(studentName);
    }

    public Teacher findTeacher(String teacherName){
        return studentRepository.findTeacher(teacherName);
    }

    public List<String> findStudentsFromTeacher(String teacher){
        return studentRepository.findStudentsFromTeacher(teacher);
    }

    public List<String> findAllStudents(){
        return studentRepository.findAllStudents();
    }

    public void deleteTeacher(String teacher){
        studentRepository.deleteTeacher(teacher);
    }

    public void deleteAllTeachers(){
        studentRepository.deleteAllTeachers();
    }

 */
