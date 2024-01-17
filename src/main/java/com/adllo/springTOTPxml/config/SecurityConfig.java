package com.adllo.springTOTPxml.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"/webSecurityConfig.xml"})
public class SecurityConfig {
    public SecurityConfig() {
        super();
    }
}
