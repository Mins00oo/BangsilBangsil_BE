package com.bangsil.bangsil.user.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.application.UserService;
import com.bangsil.bangsil.user.dto.EmailCheckDto;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@EnableWebMvc
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserService userService;

    /**
     * 사용자 등록을 위한 엔드포인트입니다.
     * 성공 시 회원가입이 완료되었다는 메시지를 반환합니다.
     * 사진을 선택하지 않을 경우 file을 null로 넘겨서 서비스 쪽에서 s3에 파일을 올리지 않도록 분기처리를 합니다.
     * {@code RequestPart} userDto 사용자 정보를 담은 DTO 객체
     * {@code RequestPart} profileImg 회원가입시 사용자가 선택한 프로필 사진
     * {@code return} 생성되었다는 메시지
     */
    @PostMapping("/signup")
    public BaseResponse<Object> createUser(@Valid @RequestPart UserDto userDto,
                                           @RequestPart(value = "profileImg") MultipartFile multipartFile) {
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

    /**
     * 사용자 등록을 위한 엔드포인트입니다.
     * 성공 시 회원가입이 완료되었다는 메시지를 반환합니다.
     * 사진을 선택하지 않을 경우 file을 null로 넘겨서 서비스 쪽에서 s3에 파일을 올리지 않도록 분기처리를 합니다.
     * {@code RequestBody} emailCheckDto 사용자 정보를 담은 DTO 객체
     * {@code return} 생성되었다는 메시지
     */
    @PostMapping("/check/email")
    public BaseResponse<Object> checkDuplicateEmail(@RequestBody EmailCheckDto emailCheckDto) {
        try {
            boolean isChecked = userService.checkDuplicateEmail(emailCheckDto.getEmail());
            return new BaseResponse<>(isChecked);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 사용자 등록을 위한 엔드포인트입니다.
     * 성공 시 회원가입이 완료되었다는 메시지를 반환합니다.
     * 사진을 선택하지 않을 경우 file을 null로 넘겨서 서비스 쪽에서 s3에 파일을 올리지 않도록 분기처리를 합니다.
     * {@code RequestBody} emailCheckDto 사용자 정보를 담은 DTO 객체
     * {@code return} 생성되었다는 메시지
     */
    @PutMapping("/user/pw")
    public BaseResponse<Object> modifyUserPw(@RequestBody ModifyPwDto modifyPwDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        if (Objects.equals(modifyPwDto.getOldPwd(), modifyPwDto.getNewPwd())) {
            return new BaseResponse<>(BaseResponseStatus.BAD_PASSWORD_REQUEST);
        }

        try {
            userService.modifyUserPw(modifyPwDto, userId);
            return new BaseResponse<>("비밀번호 변경에 성공하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/registerEmail")
    public BaseResponse<Object> authorizeEmail(@RequestParam(value = "email", required = false) String email,
                                               @RequestParam(value = "mail_key", required = false) String key) {

        try {
            userService.confirmEmail(email, key);
            return new BaseResponse<>("이메일 인증이 완료되었습니다!");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}