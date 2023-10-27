package com.webstuffy.lmsapi.controller;

import com.webstuffy.lmsapi.dto.CourseCreateRequest;
import com.webstuffy.lmsapi.entity.Course;
import com.webstuffy.lmsapi.repository.CourseRepository;
import com.webstuffy.lmsapi.service.CourseService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Log4j
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Course> addCourse(final HttpServletRequest request,
                                                            @RequestBody CourseCreateRequest courseCreateRequest   ) {

        log.info(request.getRequestURI());

        Course course = courseService.createCourse(courseCreateRequest);
        return ResponseEntity.ok(course);
    }

}
