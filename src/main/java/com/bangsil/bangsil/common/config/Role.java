package com.bangsil.bangsil.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("사용자"),
    ADMIN("관리자");

    private final String role;

}
