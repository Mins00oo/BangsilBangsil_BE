package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.common.exception.UserNotFoundException;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserProviderImpl implements UserProvider {
    private final UserRepository userRepository;

    @Override
    public User retrieveUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("유저를 조회할 수 없습니다."));
    }
}
