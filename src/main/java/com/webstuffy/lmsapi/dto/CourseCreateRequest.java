package com.webstuffy.lmsapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreateRequest {
    private long id;

    private String name;
}
