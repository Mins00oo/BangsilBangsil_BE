package com.bangsil.bangsil.common;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     * 2000번대 : Common
     * 2100번대 : User 관련
     * 2200번대 : 강의 관련
     * 2300번대 : 게시판 관련
     */
    // Common
    BAD_REQUEST(false, 2000, "입력 값이 잘못되었습니다."),

    // User

    // Course

    // Board

    /**
     * 3000 : Response 오류
     * 3000번대 : Common
     * 3100번대 : User 관련
     * 3200번대 : 강의 관련
     * 3300번대 : 게시판 관련
     */
    // Common
    NO_LOOKUP_VALUE(false, 3000, "조회된 데이터가 없습니다.");

    // User

    // Course

    // Board

    /**
     * 4000 : Database, Server 오류
     * 4000번대 : Common
     * 4100번대 : User 관련
     * 4200번대 : 강의 관련
     * 4300번대 : 게시판 관련
     */
    // Common

    // User

    // Course

    // Board


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
