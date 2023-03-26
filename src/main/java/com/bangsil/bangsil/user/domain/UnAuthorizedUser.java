package com.bangsil.bangsil.user.domain;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class UnAuthorizedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private Boolean emailAuth;

    private String emailKey;

    private String pwd;

    @Comment("프로필 사진 url")
    private String profileImgPath;

    @Comment("사진의 원본 이름")
    private String originName;

    @Comment("사진 저장 이름")
    private String saveName;

    @Comment("사용자면 USER, 관리자면 ADMIN")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public UnAuthorizedUser(String email, String emailKey, Boolean emailAuth, String pwd, S3UploadDto s3UploadDto,
                            Role role) {
        this.email = email;
        this.emailKey = emailKey;
        this.emailAuth = emailAuth;
        this.pwd = new BCryptPasswordEncoder().encode(pwd);
        if (s3UploadDto != null) {
            this.profileImgPath = s3UploadDto.getImgUrl();
            this.saveName = s3UploadDto.getSaveName();
            this.originName = s3UploadDto.getOriginName();
        }
        this.role = role;
    }

    public void changeCode(String code) {
        this.emailKey = code;
    }
}
