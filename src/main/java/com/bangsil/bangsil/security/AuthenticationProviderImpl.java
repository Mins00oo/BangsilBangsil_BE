package com.bangsil.bangsil.security;

import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.jwt.UserDetailServiceImpl;
import com.bangsil.bangsil.utils.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationProviderImpl implements AuthenticationProvider {
    private final UserDetailServiceImpl userDetailService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = token.getName();
        String password = (String) token.getCredentials();

        UserDetailsImpl userDetail;

        User user = userRepository.findByEmail(username);
        userDetail = (UserDetailsImpl) userDetailService.loadUserByUsername(username);

        if (!bCryptPasswordEncoder.matches(password, user.getPwd())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        if (user.getEmailAuth().equals(false)) {
            throw new BadCredentialsException("이메일 인증이 되지 않았습니다.");
        }

        return new UsernamePasswordAuthenticationToken(userDetail.getUsername(), "", userDetail.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
