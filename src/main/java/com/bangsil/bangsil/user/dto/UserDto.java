package com.bangsil.bangsil.user.dto;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.user.domain.UnAuthorizedUser;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String email;
    private String pwd;

    public UnAuthorizedUser toEntity(S3UploadDto s3UploadDto, String code) {
        return UnAuthorizedUser.builder()
                .email(email)
                .emailKey(code)
                .emailAuth(false)
                .pwd(pwd)
                .role(Role.USER)
                .s3UploadDto(s3UploadDto)
                .build();
    }
}
