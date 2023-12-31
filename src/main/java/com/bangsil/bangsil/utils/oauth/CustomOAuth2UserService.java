package com.bangsil.bangsil.utils.oauth;

import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.oauth.vo.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
@Configuration
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final OAuthAttributes oAuthAttributes;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = createSocialUser(attributes, registrationId);
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    public User createSocialUser(OAuthAttributes attributes, String socialType) {
        User user;

        if (userRepository.existsByEmail(attributes.getEmail())) {
            user = userRepository.findByEmail(attributes.getEmail());
            return user;
        } else
            return oAuthAttributes.toEntity(socialType);
    }
}
