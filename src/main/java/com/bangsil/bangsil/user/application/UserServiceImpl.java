package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.common.exception.*;
import com.bangsil.bangsil.user.domain.UnAuthorizedUser;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserSignUpDto;
import com.bangsil.bangsil.user.infrastructure.UnAuthorizedUserRepository;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
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
    private final UnAuthorizedUserRepository unAuthorizedUserRepository;
    private final S3UploaderService s3UploaderService;
    private final UserProvider userProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailServiceImpl emailService;

    @Transactional
    @Override
    public void createUser(UserSignUpDto userSignUpDto, MultipartFile multipartFile) {
        S3UploadDto s3UploadDto;
        String code = emailService.createCode();

        if (userRepository.existsByEmail(userSignUpDto.getEmail())) {
            throw new AlreadyExistUserException("이미 사용중인 이메일입니다.");
        }

        if (multipartFile != null) {
            try {
                s3UploadDto = s3UploaderService.upload(multipartFile, "bangsilbangsil", "userProfile");
                createUnAuthorizedUser(userSignUpDto, s3UploadDto, code);
            } catch (Exception e) {
                throw new S3UploadFailException("사진 등록에 실패하였습니다.");
            }
            sendEmailCode(userSignUpDto.getEmail(), code);

        } else {
            createUnAuthorizedUser(userSignUpDto, null, code);
            sendEmailCode(userSignUpDto.getEmail(), code);
        }
    }

    private void createUnAuthorizedUser(UserSignUpDto userSignUpDto, S3UploadDto s3UploadDto, String code) {
        UnAuthorizedUser unAuthorizedUser;
        try {
            if (unAuthorizedUserRepository.existsByEmail(userSignUpDto.getEmail())) {
                unAuthorizedUser = unAuthorizedUserRepository.findByEmail(userSignUpDto.getEmail());
            } else {
                unAuthorizedUser = userSignUpDto.toEntity(s3UploadDto, code);
            }
            unAuthorizedUserRepository.save(unAuthorizedUser);
        } catch (Exception e) {
            throw new UserDBException("회원가입에 실패했습니다.");
        }
    }

    private void sendEmailCode(String email, String code) {
        try {
            emailService.send(email, code);
        } catch (Exception e) {
            throw new EmailServiceException("인증번호 전송에 실패하였습니다.");
        }
    }

    @Transactional
    @Override
    public boolean checkDuplicateEmail(String email) throws UserNotFoundException {
        boolean b = userRepository.existsByEmail(email);
        if (!b) {
            throw new UserNotFoundException("등록된 이메일이 존재하지 않습니다");
        } else {
            return userRepository.existsByEmail(email);
        }

    }

    @Transactional
    @Override
    public void modifyUserPw(ModifyPwDto modifyPwDto, Long userId) throws NotValidInformationException {
        User user = userProvider.retrieveUser(userId);
        if (!bCryptPasswordEncoder.matches(modifyPwDto.getOldPwd(), user.getPwd())) {
            throw new NotValidInformationException("입력하신 비밀번호가 일치하지 않습니다.");
        }
        user.changePw(modifyPwDto.getNewPwd());
    }

    @Transactional
    @Override
    public void confirmEmail(String email, String key) throws BaseException {
        UnAuthorizedUser unAuthorizedUser = unAuthorizedUserRepository.findByEmail(email);
        if (!key.equals(unAuthorizedUser.getEmailKey())) {
            throw new BaseException(BaseResponseStatus.EMAIL_AUTH_FAILED);
        }

        unAuthorizedUserRepository.delete(unAuthorizedUser);

        User user = User.builder()
                .email(unAuthorizedUser.getEmail())
                .emailAuth(true)
                .pwd(unAuthorizedUser.getPwd())
                .role(Role.USER)
                .profileImgPath(unAuthorizedUser.getProfileImgPath())
                .saveName(unAuthorizedUser.getSaveName())
                .originName(unAuthorizedUser.getOriginName())
                .build();
        userRepository.save(user);

    }
}
