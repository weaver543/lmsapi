package com.webstuffy.lmsapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "course")
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long courseId;

    private String name;

    @ManyToMany(mappedBy = "courses")
    @ToString.Exclude
    private Set<Student> students = new LinkedHashSet<>();

}


