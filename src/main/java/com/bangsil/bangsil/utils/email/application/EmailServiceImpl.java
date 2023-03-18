package com.bangsil.bangsil.utils.email.application;

import com.bangsil.bangsil.common.BaseResponseStatus;
import com.bangsil.bangsil.common.exception.BaseException;
import com.bangsil.bangsil.user.domain.User;
import com.bangsil.bangsil.user.infrastructure.UserRepository;
import com.bangsil.bangsil.utils.email.dto.ConfirmCodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final RedisService redisService;

    private MimeMessage sendCode(String to, String ePw) throws BaseException {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.addRecipients(Message.RecipientType.TO, to);
            message.setSubject("인증번호가 발송되었습니다.");
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요 방실방실입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>아래 인증번호를 확인해주신 후 입력해주세요.<p>";
        msgg += "<br>";
        msgg += "<p>감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:purple;'>인증번호입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> ";
        msgg += "</div>";
        try {
            message.setText(msgg, "utf-8", "html");//내용
            message.setFrom(new InternetAddress("sunjunam118@naver.com", "Bangsil"));
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }
        return message;
    }

    public static String createCode() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 4; i++) {
            int index = rnd.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    break;
            }
        }
        return key.toString();
    }

    @Override
    @Transactional
    public void sendAuthCode(String email) throws BaseException {
        User user = userRepository.findByEmail(email);

        if (!Objects.equals(user.getEmail(), email)) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }

        String ePw = createCode();
        MimeMessage message = sendCode(email, ePw);

        try {
            javaMailSender.send(message);
            redisService.createSmsCertification(email, ePw);
        } catch (Exception es) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }

    }

    @Override
    public void confirmAuthCode(ConfirmCodeDto confirmCodeDto) throws BaseException {
        if (isVerify(confirmCodeDto)) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }
        try {
            redisService.removeSmsCertification(confirmCodeDto.getEmail());
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.BAD_REQUEST);
        }
    }

    private boolean isVerify(ConfirmCodeDto requestDto) {
        return !(redisService.hasKey(requestDto.getEmail()) &&
                redisService.getSmsCertification(requestDto.getEmail())
                        .equals(requestDto.getCode()));
    }
}
