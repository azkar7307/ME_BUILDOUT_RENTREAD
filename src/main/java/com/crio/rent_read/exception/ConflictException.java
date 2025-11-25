package com.crio.rent_read.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{

    public ConflictException(Long id, String resourceName) {
        super(resourceName + " with id '" + id + "' already exist.");
    }

    public ConflictException(String email, String resourceName) {
        super(resourceName + " with email '" + email + "' already exist.");
    }

    public ConflictException(String message) {
        super(message);
    }
}

