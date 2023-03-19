package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void createUser(UserDto userDto, MultipartFile multipartFile) throws BaseException;

    boolean checkDuplicateEmail(String email) throws BaseException;

    void modifyUserPw(ModifyPwDto modifyPwDto, Long userId) throws BaseException;
}
