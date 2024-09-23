package com.chanty.hrms.exception;

import org.springframework.web.multipart.MultipartException;

public class MultipartFileException extends MultipartException {

    public MultipartFileException(String msg) {
        super(msg);
    }
}
