package com.bangsil.bangsil.user.dto;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.user.domain.UnAuthorizedUser;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpDto {
    private String email;

    private String password;

    private String checkPassword;

    public UnAuthorizedUser toEntity(S3UploadDto s3UploadDto, String code) {
        return UnAuthorizedUser.builder()
                .email(email)
                .emailKey(code)
                .emailAuth(false)
                .pwd(password)
                .s3UploadDto(s3UploadDto)
                .build();
    }


}
