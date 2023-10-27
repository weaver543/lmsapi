package com.webstuffy.lmsapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationMessage {
    private String field;
    private String errorMessage;

}