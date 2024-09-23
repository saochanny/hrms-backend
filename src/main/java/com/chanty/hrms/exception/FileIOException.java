package com.chanty.hrms.exception;

public class FileIOException extends RuntimeException {
    public FileIOException(String message){
        super(message);
    }
    public FileIOException(String message, Throwable cause){
        super(message, cause);
    }
    public FileIOException(Throwable cause){
        super(cause);
    }
}
