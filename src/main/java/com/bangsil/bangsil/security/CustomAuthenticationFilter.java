package com.bangsil.bangsil.security;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.dto.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @SneakyThrows(BaseException.class)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        final UsernamePasswordAuthenticationToken authRequest;
        final UserLoginDto userLoginDto;

        try {
            // 사용자 요청 정보로 UserPasswordAuthenticationToken 발급
            userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            authRequest = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPwd());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
