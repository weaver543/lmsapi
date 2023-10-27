package com.webstuffy.lmsapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentCourseLog {

    public StudentCourseLog(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @EmbeddedId
    StudentCourseKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    Course course;

    String task;
    private LocalDate startDate;
    private LocalDate completionDate;

}