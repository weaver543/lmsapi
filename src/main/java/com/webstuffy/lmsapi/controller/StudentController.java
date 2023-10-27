package com.webstuffy.lmsapi.controller;

import com.webstuffy.lmsapi.dto.StudentCourseCreateRequest;
import com.webstuffy.lmsapi.dto.StudentCreateRequest;
import com.webstuffy.lmsapi.entity.Student;
import com.webstuffy.lmsapi.entity.StudentCourseLog;
import com.webstuffy.lmsapi.repository.CourseRepository;
import com.webstuffy.lmsapi.repository.StudentCourseLogRepository;
import com.webstuffy.lmsapi.repository.StudentRepository;
import com.webstuffy.lmsapi.service.StudentService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;


@Log4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentCourseLogRepository studentCourseLogRepository;

    @Autowired
    StudentService studentService;

    @SneakyThrows
    @RequestMapping(path = "students", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getStudents(final HttpServletRequest request) {

        log.debug(request.getRequestURI());

        List<Student> students = new ArrayList<Student>();
        studentRepository.findAll().forEach(students::add);
        return ResponseEntity.ok(students);
//        return ResponseEntity.ok(StudentDTOListResponse.create(students));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Student> addStudent(final HttpServletRequest request,
                                              @Valid  @RequestBody StudentCreateRequest studentCreateRequest   ) {

        log.info(request.getRequestURI());

        Student student = studentService.createStudent(studentCreateRequest);
        return ResponseEntity.ok(student);
    }

    @RequestMapping(path="log", method=RequestMethod.POST)
    public ResponseEntity<StudentCourseLog> addStudentCourseLog(final HttpServletRequest request,
                                             @Valid @RequestBody StudentCourseCreateRequest studentCourseCreateRequest   ) {

        log.info(request.getRequestURI());

        StudentCourseLog studentCourseLog = studentService.createStudentCourseLog(studentCourseCreateRequest);

        return ResponseEntity.ok(studentCourseLog);
    }

    @RequestMapping(path="register", method=RequestMethod.POST)
    public ResponseEntity<String> registerStudentForCourse(final HttpServletRequest request,
                                                                @Valid @RequestBody StudentCourseCreateRequest studentCourseCreateRequest   ) {

        log.info(request.getRequestURI());

        String studentCourseLog = studentService.registerStudentForCourse(studentCourseCreateRequest);

        return ResponseEntity.ok(studentCourseLog);
    }



}
