package com.bangsil.bangsil.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Disabled
public class JasyptConfigTest {

    @Test
    void jasypt() {
        String url = "693c5c83de59c1a45ea35b29b4de1933";
        String username = "wjEUwBMVAXEiASeRTsAsSzJX6tB2FYrk";
        String password = "qkd03010000tlf";

        String encryptUrl = jasyptEncrypt(url);
        String encryptUsername = jasyptEncrypt(username);
        String encryptPassword = jasyptEncrypt(password);

        System.out.println("encryptUrl:" + encryptUrl);
        System.out.println("encryptUsername:" + encryptUsername);
        System.out.println("encryptPassword:" + encryptPassword);

        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
    }

    private String jasyptEncrypt(String input) {
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input) {
        String key = "1234";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}
