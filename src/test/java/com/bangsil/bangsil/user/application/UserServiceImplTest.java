package com.bangsil.bangsil.user.application;

import com.bangsil.bangsil.common.config.Role;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.UnAuthorizedUser;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.dto.ModifyPwDto;
import com.bangsil.bangsil.user.dto.UserDto;
import com.bangsil.bangsil.user.infrastructure.UnAuthorizedUserRepository;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.email.application.EmailServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProvider userProvider;

    private MockMvc mockMvc;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UnAuthorizedUserRepository unAuthorizedUserRepository;

    @Mock
    private EmailServiceImpl emailServiceImpl;

    @Test
    @DisplayName("회원가입")
    void createUser() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPwd("1234");
        when(userRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(unAuthorizedUserRepository.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(emailServiceImpl.createCode()).thenReturn("1234");
        doNothing().when(emailServiceImpl).send(userDto.getEmail(), "1234");

        // When & Then
        userServiceImpl.createUser(userDto, null);

        verify(userRepository, times(1)).existsByEmail(userDto.getEmail());
        verify(unAuthorizedUserRepository, times(1)).existsByEmail(userDto.getEmail());
        verify(emailServiceImpl, times(1)).send(userDto.getEmail(), "1234");
        verify(unAuthorizedUserRepository, times(1)).save(any(UnAuthorizedUser.class));

        logger.info("회원가입 완료");
    }

    @Test
    @DisplayName("이메일 중복이 되지 않음을 처리")
    public void emailNotDuplicateCheck(TestReporter testReporter) throws Exception {
        // given
        String email = "test@gmail.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // when
        boolean isDuplicate = userServiceImpl.checkDuplicateEmail(email);

        // then
        assertFalse(isDuplicate);
    }

    @Test
    @DisplayName("이메일이 중복됨을 확인")
    public void emailDuplicateCheck() throws Exception {
        // given
        String email = "alstn7223@gmail.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // when
        boolean isDuplicate = userServiceImpl.checkDuplicateEmail(email);

        // then
        assertTrue(isDuplicate);
    }

    @Test
    @DisplayName("비밀번호 변경에 대한 테스트 코드")
    public void modifyPwd() throws BaseException {
        Long userId = 1L;
        String oldPwd = "1234";
        String newPwd = "123456";
        String hashedPwd = "$2a$10$XDV3GIoDPZZyrtfo8HYTCuZO7p3npKIOHxvFnAAiGe7Upk93EuRT.";
        ModifyPwDto modifyPwDto = new ModifyPwDto();
        modifyPwDto.setOldPwd(oldPwd);
        modifyPwDto.setNewPwd(newPwd);

        User user = new User("test@gmail.com", hashedPwd, true, "",
                "", "", Role.USER, "");
        // Mocking
        when(userProvider.retrieveUser(userId)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(modifyPwDto.getOldPwd(), hashedPwd)).thenReturn(true);

        // When
        userServiceImpl.modifyUserPw(modifyPwDto, userId);

        // Then
        verify(userProvider, times(1)).retrieveUser(userId);
        verify(bCryptPasswordEncoder, times(1)).matches(modifyPwDto.getOldPwd(), hashedPwd);
    }

    @Test
    @DisplayName("비밀번호 변경시 발생하는 예외처리의 테스트 코드")
    public void modifyPwd_exception() throws BaseException {
        Long userId = 1L;
        String oldPwd = "123456";
        String newPwd = "12345678";
        String hashedPwd = "$2a$10$XDV3GIoDPZZyrtfo8HYTCuZO7p3npKIOHxvFnAAiGe7Upk93EuRT.";
        ModifyPwDto modifyPwDto = new ModifyPwDto();
        modifyPwDto.setOldPwd(oldPwd);
        modifyPwDto.setNewPwd(newPwd);

        User user = new User("test@gmail.com", hashedPwd, true, "",
                "", "", Role.USER, "");
        // Mocking
        when(userProvider.retrieveUser(userId)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(modifyPwDto.getOldPwd(), hashedPwd)).thenReturn(false);

        assertThrows(BaseException.class, () -> {
            userServiceImpl.modifyUserPw(modifyPwDto, userId);
        });
    }
}
