package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.common.exception.NotValidInformationException;
import com.bangsil.bangsil.common.exception.UserNotFoundException;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import com.bangsil.bangsil.user.dto.UserSignUpDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void createUser(UserSignUpDto userDto, MultipartFile multipartFile) throws BaseException;

    boolean checkDuplicateEmail(String email) throws UserNotFoundException;

    void modifyUserPw(ModifyPwDto modifyPwDto, Long userId) throws NotValidInformationException;

    void confirmEmail(String email, String key) throws BaseException;
}
