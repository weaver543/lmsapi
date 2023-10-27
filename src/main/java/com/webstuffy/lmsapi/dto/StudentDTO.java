package com.webstuffy.lmsapi.dto;

import lombok.Data;
import java.util.Date;

@Data
public class StudentDTO {
    private long id;

    private String firstName;

    private String lastName;

    private Date dob;

    private String address;

    private String email;

    private String phoneNumber;

}
