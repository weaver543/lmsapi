package com.webstuffy.lmsapi.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(Throwable t) {
        super(t);
    }

    public NotFoundException(String message) {
        super(message);
    }
}