package com.bangsil.bangsil.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidInformationException extends RuntimeException {
    public NotValidInformationException(String message) {
        super(message);
    }

}
