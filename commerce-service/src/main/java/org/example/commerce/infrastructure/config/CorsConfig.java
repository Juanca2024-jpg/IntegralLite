package org.example.commerce.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry reg){
        reg.addMapping("/**").allowedOrigins("*").allowedMethods("*");
    }
}
