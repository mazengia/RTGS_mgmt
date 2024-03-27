package com.rtgs.rtgsapi.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private String message;

    public UserAlreadyExistsException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
