package com.hypad.App.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@Configuration
public class BeansConfig {
    @Bean
    public SecurityContextLogoutHandler logoutHandler(){
        return new SecurityContextLogoutHandler();
    }
}
