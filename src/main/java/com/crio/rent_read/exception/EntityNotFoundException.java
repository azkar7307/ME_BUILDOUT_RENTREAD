package com.crio.rent_read.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id, String resourceName) {
        super(resourceName + " with ID '" + id + "' not found.");
    }
}
