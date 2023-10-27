package com.webstuffy.lmsapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@Component
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    static final String EXCEPTION_MSG = "Caught {}. Message: {}. Request URI: {}. Query String: {}. Method: {}";

    @ExceptionHandler(value = { Exception.class,
            NotFoundException.class,
            ClientException.class,
            ValidationException.class,
            MethodArgumentNotValidException.class,
            ForbiddenException.class,
            ServletRequestBindingException.class
    })
    protected ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest request) {

        HttpStatus status = null;

        boolean clientException = false;
        boolean validationException = false;

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String queryString = "GET".equalsIgnoreCase(method) ? request.getQueryString() : null;

        if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            clientException = true;
        }
        else if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            clientException = true;
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException)ex;
            LOGGER.info("MethodArgumentNotValidException Exception  " + methodArgumentNotValidException.getMessage());
            LOGGER.warn(EXCEPTION_MSG, ex.getClass().getSimpleName(), ex.getMessage(), requestURI, queryString, method);
            BindingResult result = methodArgumentNotValidException.getBindingResult();
            List<FieldError> fieldErrorsList = result.getFieldErrors();
            return new ResponseEntity<>(BaseResponse.createValidationError(fieldErrorsList),status);
        }
        else if (ex instanceof ClientException) {
            status = HttpStatus.BAD_REQUEST;
            clientException = true;
        }
        else if (ex instanceof ForbiddenException) {
            status = HttpStatus.FORBIDDEN;
            clientException = true;
        }
        else if (ex instanceof ServletRequestBindingException) {
            status = HttpStatus.BAD_REQUEST;
            clientException = true;
        }
        else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (clientException) {
            // don't print stack trace for these
            LOGGER.warn(EXCEPTION_MSG, ex.getClass().getSimpleName(), ex.getMessage(), requestURI, queryString, method);
        }
        else {
            LOGGER.warn(EXCEPTION_MSG, ex.getClass().getSimpleName(), ex.getMessage(), requestURI, queryString, method, ex);
        }

        return new ResponseEntity<>(BaseResponse.createError(ex.getMessage()), status);
    }
}