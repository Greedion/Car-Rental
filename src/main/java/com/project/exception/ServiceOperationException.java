package com.project.exception;

public class ServiceOperationException extends RuntimeException{
    public ServiceOperationException() {
    }
    public ServiceOperationException(String message) {
        super(message);
    }
}
