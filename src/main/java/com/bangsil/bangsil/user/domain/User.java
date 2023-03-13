package com.bangsil.bangsil.user.domain;

import com.bangsil.bangsil.common.BaseTimeEntity;
import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String pwd;

    private String phone;

    @Column(nullable = false, unique = true)
    @Comment("사용자 닉네임")
    private String nickname;

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
    public User(String email, String pwd, String phone, String nickname, S3UploadDto s3UploadDto, Role role,
                String socialType) {
        this.email = email;
        this.pwd = new BCryptPasswordEncoder().encode(pwd);
        this.phone = phone;
        this.nickname = nickname;
        if (s3UploadDto != null) {
            this.profileImgPath = s3UploadDto.getImgUrl();
            this.saveName = s3UploadDto.getSaveName();
            this.originName = s3UploadDto.getOriginName();
        }
        this.role = role;
        this.socialType = socialType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
