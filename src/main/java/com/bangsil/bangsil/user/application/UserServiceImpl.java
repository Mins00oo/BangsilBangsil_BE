package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.dto.UserDto;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.s3.S3UploaderService;
import com.bangsil.bangsil.utils.s3.dto.S3UploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final S3UploaderService s3UploaderService;

    @Override
    public void createUser(UserDto userDto, MultipartFile multipartFile) throws BaseException {
        S3UploadDto s3UploadDto;
        if (multipartFile != null) {
            try {
                s3UploadDto = s3UploaderService.upload(multipartFile, "bangsilbangsil", "userProfile");
                User user = userDto.toEntity(s3UploadDto);
                userRepository.save(user);
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.BAD_REQUEST);
            }
        } else {
            userRepository.save(userDto.toEntity(null));
        }
    }
}
