package com.bangsil.bangsil.user.domain;

import com.bangsil.bangsil.common.BaseTimeEntity;
import com.bangsil.bangsil.common.config.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private Boolean emailAuth;

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

    @Comment("소셜 로그인 여부")
    private String socialType;

    @Builder
    public User(String email, String pwd, Boolean emailAuth, String profileImgPath, String saveName,
                String originName, Role role, String socialType) {
        this.email = email;
        this.emailAuth = emailAuth;
        this.pwd = pwd;
        if (profileImgPath != null) {
            this.profileImgPath = profileImgPath;
            this.saveName = saveName;
            this.originName = originName;
        }
        this.role = role;
        this.socialType = socialType;
    }

    public void changePw(String newPwd) {
        this.pwd = new BCryptPasswordEncoder().encode(newPwd);
    }
}
