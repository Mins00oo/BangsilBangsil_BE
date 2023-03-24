package com.bangsil.bangsil.security.handler;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(String.valueOf(authentication.getPrincipal()));

        String token = jwtTokenProvider.createToken(authentication, user.getId());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.addHeader("token", token);
        response.addHeader("userId", String.valueOf(user.getId()));

        Map<String, String> tokenDto = new HashMap<>();

        tokenDto.put("accessToken", token);
        tokenDto.put("userId", String.valueOf(user.getId()));

        BaseResponse<Map<String, String>> baseResponse = new BaseResponse<>(tokenDto);

        mapper.writeValue(response.getWriter(), baseResponse);
    }

}
