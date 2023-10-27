package com.webstuffy.lmsapi.repository;

import com.webstuffy.lmsapi.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {
    Optional<Student> findById(Long aLong);
    Student findByEmail(String email);
}
