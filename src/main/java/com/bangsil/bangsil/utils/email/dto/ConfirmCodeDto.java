package com.bangsil.bangsil.utils.email.dto;

import lombok.Getter;

@Getter
public class ConfirmCodeDto {
    private String email;
    private String code;
}
