package com.bangsil.bangsil.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.isSuccess = BaseResponseStatus.SUCCESS.isSuccess();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
        this.code = BaseResponseStatus.SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
        this.result = null;
    }

    // 요청에 실패한 경우 (BadRequest)
    public BaseResponse(String details, String timestamp) {
        this.isSuccess = BaseResponseStatus.BAD_REQUEST.isSuccess();
        this.message = details;
        this.code = BaseResponseStatus.BAD_REQUEST.getCode();
        this.result = null;
    }
}
