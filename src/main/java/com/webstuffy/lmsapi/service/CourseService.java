package com.webstuffy.lmsapi.service;

import com.webstuffy.lmsapi.dto.CourseCreateRequest;
import com.webstuffy.lmsapi.entity.Course;
import com.webstuffy.lmsapi.exception.ValidationException;
import com.webstuffy.lmsapi.repository.CourseRepository;
import org.springframework.stereotype.Service;


@Service
public class CourseService {

    CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(CourseCreateRequest courseCreateRequest) {

        if (courseRepository.findByName(courseCreateRequest.getName()) != null ) {
            throw new ValidationException("Course name already exists: " + courseCreateRequest.getName() );
        }


        Course course = new Course();
//        course.setId(courseCreateRequest.getId());
        course.setName(courseCreateRequest.getName());
        courseRepository.save(course);
        return course;
    }
}
