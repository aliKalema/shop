package com.phenomenal.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SecurityBeanUtils {
    @Bean
    public AuthSuccessHandler authSuccessHandler() {
        return new AuthSuccessHandler();
    }
}
