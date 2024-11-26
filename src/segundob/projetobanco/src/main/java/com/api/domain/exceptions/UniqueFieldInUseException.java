package com.api.domain.exceptions;

public class UniqueFieldInUseException extends RuntimeException {
    public UniqueFieldInUseException(String message) {
        super(message);
    }
}
