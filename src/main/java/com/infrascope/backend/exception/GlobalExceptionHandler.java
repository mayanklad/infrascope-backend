package com.infrascope.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EmptyFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(EmptyFileException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ResponseBody
    public ErrorResponse handleException(MaxUploadSizeExceededException e) {
        return new ErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), "File size exceeds 5MB limit.");
    }

    @ExceptionHandler(value = UnsupportedOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleException(UnsupportedOperationException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
