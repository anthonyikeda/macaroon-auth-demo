package com.example.secureapione.config;

import com.example.secureapione.service.MacaroonUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Anthony Ikeda <anthony.ikeda@gmail.com> on 3/06/2018.
 */
@Configuration
@PropertySource(value = "classpath:macaroon.properties")
public class MacaroonConfig {

    @Value("${security.macaroon.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MacaroonUserDetailsService();
    }
}
