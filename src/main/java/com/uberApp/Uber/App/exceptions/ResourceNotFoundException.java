package com.uberApp.Uber.App.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
