package com.bangsil.bangsil.utils.oauth.handler;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> kakao_account;
        Map<String, Object> kakao_profile;
        String email;
        String socialType;
        String nickName;
        String mobile = null;
        String gender = null;

        if (oAuth2User.getAttributes().containsKey("kakao_account")) {
            socialType = "kakao";
            kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
            kakao_profile = (Map<String, Object>) kakao_account.get("profile");
            email = String.valueOf(kakao_account.get("email"));
            nickName = String.valueOf(kakao_profile.get("nickname").toString());
        } else if (oAuth2User.getAttributes().containsKey("mobile")) {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickName = String.valueOf(oAuth2User.getAttributes().get("nickname"));
            mobile = String.valueOf(oAuth2User.getAttributes().get("mobile"));
            gender = String.valueOf(oAuth2User.getAttributes().get("gender"));
            socialType = "naver";
        } else {
            email = String.valueOf(oAuth2User.getAttributes().get("email"));
            nickName = String.valueOf(oAuth2User.getAttributes().get("name"));
            socialType = "google";
        }

        User user = userRepository.findByEmail(email);
        String url;
        if (userRepository.existsByEmail(email)) {
            String socialToken = jwtTokenProvider.createSocialToken(user.getId());
            url = sendExistInfoToRedirectUrI(socialToken, user.getId());
            response.addHeader("Authorization", socialToken);
            getRedirectStrategy().sendRedirect(request, response, url);
        } else {
            if (socialType.equals("naver")) {
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                url = sendInfoToNaverRedirectUrl(email, nickName, socialType, mobile, gender);
            } else {
                url = sendInfoToRedirectUrl(email, nickName, socialType);
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            }
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String sendExistInfoToRedirectUrI(String token, Long userId) {
        String u = "?userId=";
        return UriComponentsBuilder.fromUriString("https://nanum.vercel.app/outh/redirect"
                        + u + userId + "&role=" + Role.USER + "&token=" + token)
                .build().toUriString();
    }

    private String sendInfoToRedirectUrl(String email, String nickName, String socialType) {
        String e = "?email=";
        String n = "&nickname=";
        String s = "&socialType=";
        String encode = URLEncoder.encode(nickName, StandardCharsets.UTF_8);

        return UriComponentsBuilder.fromUriString("https://nanum.vercel.app/outh/redirect" + e + email + n + encode + s + socialType)
                .build().toUriString();
    }

    private String sendInfoToNaverRedirectUrl(String email, String nickName, String socialType, String mobile, String gender) {
        String e = "?email=";
        String n = "&nickname=";
        String s = "&socialType=";
        String m = "&mobile=";
        String g = "&gender=";

        String encode = URLEncoder.encode(nickName, StandardCharsets.UTF_8);

        String num = mobile.replaceAll("-", "");

        return UriComponentsBuilder.fromUriString("https://nanum.vercel.app/outh/redirect" + e + email
                        + n + encode + s + socialType + m + num + g + gender)
                .build().toUriString();
    }

}
