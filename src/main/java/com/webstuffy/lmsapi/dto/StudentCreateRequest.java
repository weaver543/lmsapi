package com.webstuffy.lmsapi.dto;

import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class StudentCreateRequest {
    @Id
    private long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "Date of birth is mandatory")
    private String dob;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;
}
