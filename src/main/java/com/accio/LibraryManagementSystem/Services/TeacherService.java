package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Repository.StudentRepository;
import com.accio.LibraryManagementSystem.Repository.TeacherRepository;
import com.accio.LibraryManagementSystem.Requests.UpdateTeacherRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TeacherService
{
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

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

    public String updateTeacher(Integer teacherId, UpdateTeacherRequest teacherRequest)throws Exception
    {
        try
        {
            Teacher teacher=teacherRepository.findById(teacherId)
                    .orElseThrow(()->new RuntimeException("Teacher not found"));

            teacher.setNoOfStudents(teacherRequest.getNoOfStudents()!=null?teacherRequest.getNoOfStudents():teacher.getNoOfStudents());
            teacher.setName(teacherRequest.getName()!=null?teacherRequest.getName():teacher.getName());
            teacher.setBranch(teacherRequest.getBranch()!=null?teacherRequest.getBranch():teacher.getBranch());
            teacher.setAge(teacherRequest.getAge()!=null?teacherRequest.getAge():teacher.getAge());
            teacher.setAddress(teacherRequest.getAddress()!=null?teacherRequest.getAddress():teacher.getAddress());
            teacher.setEmail(teacherRequest.getEmail()!=null?teacherRequest.getEmail():teacher.getEmail());

            teacherRepository.save(teacher);
            return "The teacher has been updated";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public List<Student> findAllStudentsDetails(String teacherName)
    {
        List<Student> studentList=studentRepository.findAll();
        List<Student> ans=new ArrayList<>();
        for(Student student:studentList)
        {
            if(student.getTeacher().getName().equals(teacherName))
            {
                ans.add(student);
            }
        }
        return ans;
    }

    public List<String> findAllStudents(String teacherName)
    {
        List<Student> studentList=studentRepository.findAll();
        List<String> ans=new ArrayList<>();
        for(Student student:studentList)
        {
            if(student.getTeacher().getName().equals(teacherName))
            {
                ans.add(student.getName());
            }
        }
        return ans;
    }
}
