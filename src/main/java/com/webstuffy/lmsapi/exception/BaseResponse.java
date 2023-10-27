package com.webstuffy.lmsapi.exception;

import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseResponse {

    private String status = "success";

    private String message;

    private List<ValidationMessage> validationErrorMessage;

    public static BaseResponse createSuccess(String message) {
        BaseResponse response = new BaseResponse();
        response.setStatus("success");
        response.setMessage(message);
        return response;
    }

    public static BaseResponse createError(String message) {
        BaseResponse response = new BaseResponse();
        response.setStatus("error");
        response.setMessage(message);
        return response;
    }
    public static BaseResponse createValidationError(List<FieldError> fieldErrorsList) {
        List<ValidationMessage> validaionMessageList = new ArrayList<>();

        for (FieldError fieldError : fieldErrorsList) {
            ValidationMessage validationMessage = new ValidationMessage();
            validationMessage.setField(fieldError.getField());
            validationMessage.setErrorMessage(fieldError.getDefaultMessage());
            validaionMessageList.add(validationMessage);
        }
        BaseResponse response = new BaseResponse();
        response.setStatus("error");
        response.setValidationErrorMessage(validaionMessageList);
        return response;
    }

    public void setSuccess() {
        this.status = "success";
    }

    public void setError() {
        this.status = "error";
    }

    public boolean isSuccess() {
        return !"error".equals(this.status);
    }

}