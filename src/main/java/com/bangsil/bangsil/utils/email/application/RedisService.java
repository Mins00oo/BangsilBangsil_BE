package com.bangsil.bangsil.utils.email.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.utils.oauth.vo.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class RedisService {
    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void createSmsCertification(String email, String certificationNumber) throws BaseException {
        try {
            ValueOperations<String, String> vop = redisTemplate.opsForValue();
            vop.set(PREFIX + email, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }
    }

    public User createTemporalOAuthUser(String email, String nickname, OAuthAttributes attributes, String socialType) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("email", email, Duration.ofSeconds(60 * 10));
        vop.set("nickname", nickname, Duration.ofSeconds(60 * 10));
        vop.set("socialType", socialType, Duration.ofSeconds(60*10));

        return User.builder()
                .email(vop.get("email"))
                .nickname(vop.get("nickname"))
                .socialType(vop.get("socialType"))
                .build();
    }

    public String getSmsCertification(String email) { // (4)
        return redisTemplate.opsForValue().get(PREFIX + email);
    }

    public void removeSmsCertification(String email) { // (5)
        redisTemplate.delete(PREFIX + email);
    }

    public boolean hasKey(String email) {  //(6)
        return redisTemplate.hasKey(PREFIX + email);
    }
}
