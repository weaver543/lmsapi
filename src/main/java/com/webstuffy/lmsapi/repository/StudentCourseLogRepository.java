package com.webstuffy.lmsapi.repository;

import com.webstuffy.lmsapi.entity.Student;
import com.webstuffy.lmsapi.entity.StudentCourseLog;
import com.webstuffy.lmsapi.entity.StudentCourseKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentCourseLogRepository extends CrudRepository<StudentCourseLog, StudentCourseKey> {
    List<StudentCourseLog> findByStudent(Student student);

}
