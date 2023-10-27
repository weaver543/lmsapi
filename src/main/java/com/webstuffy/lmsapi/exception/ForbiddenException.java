package com.webstuffy.lmsapi.exception;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ForbiddenException(Throwable t) {
        super(t);
    }

    public ForbiddenException(String message) {
        super(message);
    }

}
