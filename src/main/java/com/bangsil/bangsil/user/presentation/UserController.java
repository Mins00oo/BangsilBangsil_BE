package com.bangsil.bangsil.user.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.application.UserService;
import com.bangsil.bangsil.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public BaseResponse<Object> createUser(@RequestPart UserDto userDto,
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

}
