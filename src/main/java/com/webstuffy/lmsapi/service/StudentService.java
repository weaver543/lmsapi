package com.webstuffy.lmsapi.service;

import com.webstuffy.lmsapi.dao.StudentCourseDAO;
import com.webstuffy.lmsapi.dto.StudentCourseCreateRequest;
import com.webstuffy.lmsapi.dto.StudentCreateRequest;
import com.webstuffy.lmsapi.entity.*;
//import com.webstuffy.lmsapi.repository.StudentCourseRepository;
import com.webstuffy.lmsapi.exception.NotFoundException;
import com.webstuffy.lmsapi.exception.ValidationException;
import com.webstuffy.lmsapi.repository.CourseRepository;
import com.webstuffy.lmsapi.repository.StudentCourseLogRepository;
import com.webstuffy.lmsapi.repository.StudentRepository;
import com.webstuffy.lmsapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDate.now;

@Service
public class StudentService {
    public static String DATE_FORMAT = "yyyy-MM-dd";
    public static Integer MINIMUM_AGE = 16;
    public static Integer MAX_CLASSES_PER_STUDENT = 3;


    StudentRepository studentRepository;
    CourseRepository courseRepository;
    TaskRepository taskRepository;

    StudentCourseDAO studentCourseDAO;
    private final StudentCourseLogRepository studentCourseLogRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository,
                          StudentCourseLogRepository studentCourseLogRepository,  TaskRepository taskRepository, StudentCourseDAO studentCourseDAO) {
        this.studentRepository = studentRepository;;
        this.courseRepository = courseRepository;;
        this.studentCourseLogRepository = studentCourseLogRepository;
        this.taskRepository  = taskRepository;
        this.studentCourseDAO = studentCourseDAO;
    }

    public Student createStudent(StudentCreateRequest studentCreateRequest) {
        LocalDate birthDate;

        if (studentRepository.findByEmail(studentCreateRequest.getEmail()) != null ) {
            throw new ValidationException("Email " + studentCreateRequest.getEmail() + " already exists");
        }

        try {
            birthDate = LocalDate.parse(studentCreateRequest.getDob(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            throw new ValidationException("Invalid date: must be in format " + DATE_FORMAT);
        }

        int age = Period.between(birthDate, now()).getYears();
        if (age < MINIMUM_AGE) {
            throw new ValidationException("To register, you must be at least " + MINIMUM_AGE);
        }

        Student student = new Student();
        student.setFirstName(studentCreateRequest.getFirstName());
        student.setLastName(studentCreateRequest.getLastName());
        student.setDob(birthDate);
        student.setEmail(studentCreateRequest.getEmail());
        student.setAddress(studentCreateRequest.getAddress());
        student.setPhoneNumber(studentCreateRequest.getPhoneNumber());
        studentRepository.save(student);
        return student;
    }

    public StudentCourseLog createStudentCourseLog(StudentCourseCreateRequest studentCourseCreateRequest) {
        Student student = studentRepository.findById(studentCourseCreateRequest.studentId).orElseThrow(() -> new NotFoundException("No student found"));

        List<StudentCourseLog> studentCourseLogs = studentCourseLogRepository.findByStudent(student);
        if (studentCourseLogs.size() == MAX_CLASSES_PER_STUDENT) {
            throw new ValidationException("Maximum number of classes (" + MAX_CLASSES_PER_STUDENT + ") already registered for this student");
        }

        Course course = courseRepository.findById(studentCourseCreateRequest.courseId).orElseThrow(() -> new NotFoundException("No course found"));
        StudentCourseLog studentCourseLog = new StudentCourseLog(student, course);
        studentCourseLogRepository.save(studentCourseLog);
        return studentCourseLog;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        taskRepository.save(new Task("RESEARCHING", "Do research") );
        taskRepository.save(new Task("PRACTICING", "Practice what was learned") );
        taskRepository.save(new Task("WATCHING_VIDEOS", "Watch videaos from the class") );
    }


    public String registerStudentForCourse(StudentCourseCreateRequest studentCourseCreateRequest) {
        Student student = studentRepository.findById(studentCourseCreateRequest.studentId).orElseThrow(() -> new NotFoundException("No student found"));

        List<StudentCourseLog> studentCourseLogs = studentCourseLogRepository.findByStudent(student);
        if (studentCourseLogs.size() == MAX_CLASSES_PER_STUDENT) {
            throw new ValidationException("Maximum number of classes (" + MAX_CLASSES_PER_STUDENT + ") already registered for this student");
        }

        Course course = courseRepository.findById(studentCourseCreateRequest.courseId).orElseThrow(() -> new NotFoundException("No course found"));

        student.getCourses().add(course);
        course.getStudents().add(student);
        studentRepository.save(student);
        courseRepository.save(course);


        return "yes";
//        studentCourseDAO.create(student.getStudentId(), course.getCourseId());
//        return studentCourseLog;
    }




}
