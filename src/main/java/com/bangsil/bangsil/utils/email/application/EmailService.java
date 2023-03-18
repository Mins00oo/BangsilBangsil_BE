package com.bangsil.bangsil.utils.email.application;

import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.utils.email.dto.ConfirmCodeDto;

public interface EmailService {
    void sendAuthCode(String email) throws BaseException;

    void confirmAuthCode(ConfirmCodeDto confirmCodeDto) throws BaseException;
}
