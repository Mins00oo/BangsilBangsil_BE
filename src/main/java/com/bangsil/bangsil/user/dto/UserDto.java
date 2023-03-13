package com.bangsil.bangsil.user.dto;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.user.domain.User;
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
    private String nickname;
    private Role role;
    private String phone;
    private String profileImgUrl;

    public User toEntity(S3UploadDto s3UploadDto) {
        return User.builder()
                .email(email)
                .pwd(pwd)
                .nickname(nickname)
                .role(role)
                .phone(phone)
                .s3UploadDto(s3UploadDto)
                .build();
    }
}
