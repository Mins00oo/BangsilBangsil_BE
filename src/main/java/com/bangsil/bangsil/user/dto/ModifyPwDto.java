package com.bangsil.bangsil.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModifyPwDto {
    private String oldPwd;
    private String newPwd;
}
