package com.chanty.hrms.exception;

public class MailException extends RuntimeException{
    public MailException(Throwable cause) {
        super(cause.getMessage() , cause);
    }
    public MailException(String message) {
        super(message);
    }
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
}
