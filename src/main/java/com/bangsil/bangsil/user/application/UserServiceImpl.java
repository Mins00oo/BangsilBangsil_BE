package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.email.application.EmailHandler;
import com.bangsil.bangsil.utils.email.application.EmailServiceImpl;
import com.bangsil.bangsil.utils.s3.S3UploaderService;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3UploaderService s3UploaderService;
    private final UserProvider userProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailServiceImpl emailService;

    @Transactional
    @Override
    public void createUser(UserDto userDto, MultipartFile multipartFile) throws BaseException {
        S3UploadDto s3UploadDto;
        String code = emailService.createCode();
        User user;
        if (multipartFile != null) {
            try {
                s3UploadDto = s3UploaderService.upload(multipartFile, "bangsilbangsil", "userProfile");
                user = userDto.toEntity(s3UploadDto, code);
                userRepository.save(user);
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.USER_CREATE_FAILED);
            }

            try {
                emailService.send(user.getEmail(), code);
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.BAD_EMAIL_REQUEST);
            }

        } else {
            User save = userRepository.save(userDto.toEntity(null, code));

            try {
                emailService.send(save.getEmail(), code);
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.BAD_REQUEST);
            }
        }
    }

    @Transactional
    @Override
    public boolean checkDuplicateEmail(String email) throws BaseException {
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_EMAIL_REQUEST);
        }
    }

    @Transactional
    @Override
    public void modifyUserPw(ModifyPwDto modifyPwDto, Long userId) throws BaseException {
        User user = userProvider.retrieveUser(userId);

        if (!bCryptPasswordEncoder.matches(modifyPwDto.getOldPwd(), user.getPwd())) {
            throw new BaseException(BaseResponseStatus.INCORRECT_USER_PASSWORD);
        }

        user.changePw(modifyPwDto.getNewPwd());

    }

    @Transactional
    @Override
    public void confirmEmail(String email, String key) throws BaseException {
        User user = userRepository.findByEmail(email);
        if (!user.getEmailKey().equals(key)) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }

        user.emailVerifiedSuccess();
    }
}
