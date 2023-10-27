package com.webstuffy.lmsapi.exception;

public class ClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClientException(Throwable t) {
        super(t);
    }

    public ClientException(String message) {
        super(message);
    }

}
