package com.bangsil.bangsil.utils.oauth.vo;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Configuration
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private Role role;

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
        return OAuthAttributes.builder()
                .email(String.valueOf(kakao_account.get("email")))
                .attributes(attributes)
                .nameAttributeKey("id")
                .role(Role.USER)
                .build();
    }

    public User toEntity(String socialType) {
        return User.builder()
                .email(email)
                .pwd(new BCryptPasswordEncoder().encode("1234"))
                .role(Role.USER)
                .socialType(socialType)
                .build();
    }
}
