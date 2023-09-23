package com.bangsil.bangsil.user.presentation;

import com.bangsil.bangsil.common.BaseResponse;
import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.common.exception.NotValidInformationException;
import com.bangsil.bangsil.common.exception.UserNotFoundException;
import com.bangsil.bangsil.user.application.UserService;
import com.bangsil.bangsil.user.dto.EmailCheckDto;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import com.bangsil.bangsil.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> createUser(@Valid @RequestPart UserSignUpDto userDto,
                                             @RequestPart(value = "profileImg") MultipartFile multipartFile) {
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                userService.createUser(userDto, multipartFile);
            } else {
                userService.createUser(userDto, null);
            }
            String result = "회원가입이 완료되었습니다.";
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
        } catch (BaseException e) {
            throw new UserNotFoundException("정보가 일치하지 않습니다.");
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
    public ResponseEntity<Object> checkDuplicateEmail(@RequestBody EmailCheckDto emailCheckDto) {
        try {
            userService.checkDuplicateEmail(emailCheckDto.getEmail());
            String result = "사용 가능한 이메일입니다.";
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
        } catch (UserNotFoundException e) {
            throw e;
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
    public ResponseEntity<Object> modifyUserPw(@RequestBody ModifyPwDto modifyPwDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        if (Objects.equals(modifyPwDto.getOldPwd(), modifyPwDto.getNewPwd())) {
            throw new NotValidInformationException("변경하려는 비밀번호가 동일합니다.");
        }

        try {
            userService.modifyUserPw(modifyPwDto, userId);
            String result = "비밀번호가 수정되었습니다.";
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<>(result));
        } catch (NotValidInformationException e) {
            throw e;
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