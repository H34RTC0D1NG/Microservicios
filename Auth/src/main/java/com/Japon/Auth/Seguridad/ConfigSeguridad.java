package com.Japon.Auth.Seguridad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration 
public class ConfigSeguridad {

    @Bean
    public BCryptPasswordEncoder Encriptador() {
        return new BCryptPasswordEncoder();
    }

}