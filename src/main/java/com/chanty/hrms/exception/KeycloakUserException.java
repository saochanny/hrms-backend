package com.chanty.hrms.exception;

public class KeycloakUserException extends RuntimeException {
    public KeycloakUserException(String message){
        super(message);
    }
}
