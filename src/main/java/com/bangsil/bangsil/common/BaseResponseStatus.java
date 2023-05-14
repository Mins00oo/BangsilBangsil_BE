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
    SUCCESS_NULLPOINT(true, 1001, "요청에 성공하였지만 빈 값이 존재합니다."),

    /**
     * 2000 : Request 오류
     * 2000번대 : Room 관련
     * 2100번대 : User 관련
     * 2200번대 :
     * 2300번대 :
     */
    // Common
    BAD_REQUEST(false, 2000, "입력 값이 잘못되었습니다."),

    // Room

    // User
    BAD_EMAIL_REQUEST(false, 2100, "이메일 입력 값이 잘못되었습니다."),
    BAD_PASSWORD_REQUEST(false, 2101, "비밀번호 입력 값이 잘못되었습니다."),
    LOGIN_REQUEST_FAILED(false, 2200, "로그인에 실패했습니다."),


    /**
     * 3000 : Response 오류
     * 3000번대 : Room 관련
     * 3100번대 : User 관련
     * 3200번대 :
     * 3300번대 :
     */
    // Common
    NO_LOOKUP_VALUE(false, 3000, "조회된 데이터가 없습니다."),
    ROOM_CREATE_FAILED(false, 3100, "방 정보 생성에 실패했습니다."),

    // Room

    // User

    /**
     * 4000 : Database, Server 오류
     * 4000번대 : Room 관련
     * 4100번대 : User 관련
     * 4200번대 :
     * 4300번대 :
     */
    // Common

    // Room

    // User
    USER_CREATE_FAILED(false, 4100, "유저 생성에 실패했습니다."),
    USER_RETRIEVE_FAILED(false, 4101, "유저 정보 조회에 실패했습니다."),
    USER_MODIFY_FAILED(false, 4102, "유저 정보 수정에 실패했습니다."),
    USER_DELETE_FAILED(false, 4103, "유저 정보 삭제에 실패했습니다."),
    ALREADY_EXIST_USER(false, 4104, "이미 회원정보가 존재합니다."),
    INCORRECT_USER_PASSWORD(false, 4104, "유저의 비밀번호가 일치하지 않습니다"),
    EMAIL_AUTH_FAILED(false, 4105, "이메일 인증이 되지않은 유저입니다.");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
