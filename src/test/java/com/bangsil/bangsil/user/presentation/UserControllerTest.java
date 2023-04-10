package com.bangsil.bangsil.user.presentation;

import com.amazonaws.util.IOUtils;
import com.bangsil.bangsil.user.application.UserService;
import com.bangsil.bangsil.user.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 컨트롤러 메서드 테스트")
    public void createUserTest() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("testEmail");
        userDto.setPwd("testPassword");

        String value = objectMapper.writeValueAsString(userDto);

        byte[] imageBytes = new byte[0];
        try {
            InputStream inputStream = new FileInputStream("src/test/resources/testImage/testImage1.png");
            imageBytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MockMultipartFile dto = new MockMultipartFile("userDto", "", "application/json", value.getBytes());
        MockMultipartFile profileImg = new MockMultipartFile("profileImg", "testImage1", "image/png", imageBytes);

        doNothing().when(userService).createUser(userDto, profileImg);

        mockMvc.perform(multipart("/api/v1/signup")
                        .file(dto)
                        .file(profileImg)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
