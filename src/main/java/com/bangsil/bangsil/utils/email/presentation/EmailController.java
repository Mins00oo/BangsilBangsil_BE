package com.bangsil.bangsil.utils.email.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.utils.email.application.EmailService;
import com.bangsil.bangsil.utils.email.dto.ConfirmCodeDto;
import com.bangsil.bangsil.utils.email.dto.SendAuthCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send/code")
    public BaseResponse<Object> sendAuthCode(@RequestBody SendAuthCodeDto authCodeDto) {
        try {
            emailService.sendAuthCode(authCodeDto.getEmail());
            return new BaseResponse<>("인증번호가 발송되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/confirm/code")
    public BaseResponse<Object> confirmAuthCode(@RequestBody ConfirmCodeDto confirmCodeDto) {
        try {
            emailService.confirmAuthCode(confirmCodeDto);
            return new BaseResponse<>("인증번호가 확인되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
