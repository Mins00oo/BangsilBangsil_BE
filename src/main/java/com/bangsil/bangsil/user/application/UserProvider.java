package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.User;

public interface UserProvider {
    User retrieveUser(Long userId) throws BaseException;
}
