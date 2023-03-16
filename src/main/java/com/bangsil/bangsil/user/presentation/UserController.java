package com.bangsil.bangsil.user.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.application.UserService;
import com.bangsil.bangsil.user.dto.EmailCheckDto;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import com.bangsil.bangsil.utils.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public BaseResponse<Object> createUser(@Valid @RequestPart UserDto userDto,
                                           @RequestPart(value = "profileImg", required = false) MultipartFile multipartFile) {
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                userService.createUser(userDto, multipartFile);
            } else {
                userService.createUser(userDto, null);
            }
            return new BaseResponse<>("회원가입이 완료되었습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/check/email")
    public BaseResponse<Object> checkDuplicateEmail(@RequestBody EmailCheckDto emailCheckDto) {
        try {
            boolean isChecked = userService.checkDuplicateEmail(emailCheckDto.getEmail());
            return new BaseResponse<>(isChecked);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PutMapping("/user/pw")
    public BaseResponse<Object> modifyUserPw(@RequestBody ModifyPwDto modifyPwDto, HttpServletRequest request) {
        String token = jwtTokenProvider.getToken(request);
        Long userId = Long.valueOf(jwtTokenProvider.getUserPk(token));

        if (Objects.equals(modifyPwDto.getOldPwd(), modifyPwDto.getNewPwd())) {
            return new BaseResponse<>(BaseResponseStatus.BAD_REQUEST);
        }

        try {
            userService.modifyUserPw(modifyPwDto, userId);
            return new BaseResponse<>("비밀번호 변경에 성공하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

}
