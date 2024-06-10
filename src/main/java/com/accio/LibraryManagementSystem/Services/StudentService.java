package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Controllers.TeacherController;
import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Models.Student;
import com.accio.LibraryManagementSystem.Models.Teacher;
import com.accio.LibraryManagementSystem.Repository.StudentRepository;
import com.accio.LibraryManagementSystem.Repository.TeacherRepository;
import com.accio.LibraryManagementSystem.Requests.UpdateStudentRequest;
import com.accio.LibraryManagementSystem.Responses.TeacherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService
{
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

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
        Student student=studentRepository.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));
        studentRepository.delete(student);
        return "Student with student-id: "+studentId+" has been removed from the database";
    }

    public String updateStudent(Integer studentId, UpdateStudentRequest studentRequest) throws Exception
    {
        try
        {
            Student student=studentRepository.findById(studentId)
                    .orElseThrow(() -> new NoSuchElementException("Student not found with ID: " + studentId));

            student.setName(studentRequest.getName()!=null?studentRequest.getName():student.getName());
            student.setAge(studentRequest.getAge()!=null?studentRequest.getAge():student.getAge());
            student.setEmail(studentRequest.getEmail()!=null?studentRequest.getEmail():student.getEmail());
            student.setAddress(studentRequest.getAddress()!=null?studentRequest.getAddress():student.getAddress());
            student.setBranch(studentRequest.getBranch()!=null?studentRequest.getBranch():student.getBranch());

            if(!studentRequest.getTeacherName().equals(student.getTeacher().getName()))
            {
                // Update student's current teacher's number of students
                Teacher currentTeacher = student.getTeacher();
                currentTeacher.setNoOfStudents(currentTeacher.getNoOfStudents() - 1);
                teacherRepository.save(currentTeacher);

                Teacher teacher = teacherRepository.findByName(studentRequest.getTeacherName());
                if(teacher==null)
                {
                    throw new RuntimeException("Teacher not found with name: " + studentRequest.getTeacherName());
                }
                        //.orElseThrow(() -> new NoSuchElementException("Teacher not found with name: " + studentRequest.getTeacherName()));
                // Associate student with new teacher
                associateStudentAndTeacher(studentId, teacher.getTeacherId());
            }
            studentRepository.save(student);
            return "The student has been updated";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public String associateStudentAndTeacher(Integer studentID, Integer teacherID) throws Exception
    {
        try
        {
            Optional<Student> optionalStudent = studentRepository.findById(studentID);
            if (optionalStudent.isEmpty())
            {
                throw new Exception("Entered student-ID is invalid");
            }
            Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherID);
            if (optionalTeacher.isEmpty())
            {
                throw new Exception("Entered teacher-ID is invalid");
            }

            Student student = optionalStudent.get();
            Teacher teacher = optionalTeacher.get();

            student.setTeacher(teacher);
            teacher.setNoOfStudents(teacher.getNoOfStudents()+1);
            //changed in both so save in both
            studentRepository.save(student);
            teacherRepository.save(teacher);

            return "Student and Teacher has been associated";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public TeacherResponse findTeacher(Integer studentId,String studentName)throws Exception
    {
        Student student=studentRepository.findById(studentId)
                .orElseThrow(()->new RuntimeException("Student id is invalid"));
        if(!student.getName().equals(studentName))
        {
            throw new Exception("Student name is invalid");
        }

        return TeacherResponse.builder()
                .name(student.getTeacher().getName())
                .age(student.getTeacher().getAge())
                .branch(student.getTeacher().getBranch())
                .noOfStudents(student.getTeacher().getNoOfStudents())
                .email(student.getTeacher().getEmail())
                .build();
    }
}
