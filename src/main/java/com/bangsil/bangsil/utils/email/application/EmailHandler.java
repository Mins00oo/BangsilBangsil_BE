package com.bangsil.bangsil.utils.email.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailHandler {
    private final JavaMailSender javaMailSender;

    public void send(String email, String emailKey) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("방실방실 인증메일입니다.");
        message.setText(
                "<h1>방실방실 메일인증</h1>" +
                        "<br>방실방실에 오신것을 환영합니다." +
                        "<br>아래 [이메일 인증 확인]을 눌러주세요" +
                        "<br><a href='https://bangsil.co.kr/api/v1/registerEmail?email=" + email +
                        "&mail_key=" + emailKey +
                        "' target='_blank'>이메일 인증 확인</a>"
        );
        message.setFrom("방실방실");
        javaMailSender.send(message);
    }

    public String createCode() {
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
}
