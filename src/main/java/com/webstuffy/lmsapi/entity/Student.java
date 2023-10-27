package com.webstuffy.lmsapi.entity;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long studentId;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String address;

    private String email;

    private String phoneNumber;

    @ManyToMany
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ToString.Exclude
    private Set<Course> courses = new LinkedHashSet<>();

}
