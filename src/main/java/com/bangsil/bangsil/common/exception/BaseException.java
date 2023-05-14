package com.bangsil.bangsil.common.exception;

import com.bangsil.bangsil.common.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
    private BaseResponseStatus status;
    public BaseException(BaseResponseStatus status,Throwable cause){
        super(status.getMessage(),cause);
    }
    public BaseException(BaseResponseStatus status){
        super(status.getMessage());
        this.status = status;
    }
}