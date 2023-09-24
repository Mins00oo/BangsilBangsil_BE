package com.bangsil.bangsil.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailServiceException extends RuntimeException {
    public EmailServiceException(String message) {
        super(message);
    }

}
