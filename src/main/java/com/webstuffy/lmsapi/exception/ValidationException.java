package com.webstuffy.lmsapi.exception;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidationException(Throwable t) {
        super(t);
    }

    public ValidationException(String message) {
        super(message);
    }

}
