package com.andef.javalabs.lab6.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id=" + id + " was not found");
    }
}
