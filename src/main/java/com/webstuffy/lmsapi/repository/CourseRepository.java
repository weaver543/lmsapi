package com.webstuffy.lmsapi.repository;

import com.webstuffy.lmsapi.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findByName(String name);
}
