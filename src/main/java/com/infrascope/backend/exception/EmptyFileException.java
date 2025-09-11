package com.infrascope.backend.exception;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException(String message) {
        super(message);
    }
}
