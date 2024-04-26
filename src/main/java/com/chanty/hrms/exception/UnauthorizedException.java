package com.chanty.hrms.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnauthorizedException  extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}
