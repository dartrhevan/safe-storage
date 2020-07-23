package com.example.safestorage.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncryptionConfig {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return encoder;
    }

}
