package com.rtgs.rtgsapi.exceptions;

public class RuntimeExceptions extends RuntimeException {
    private String message;

    public RuntimeExceptions(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
