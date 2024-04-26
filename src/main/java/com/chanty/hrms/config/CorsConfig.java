package com.chanty.hrms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("http://localhost:3000","http://localhost:5173","http://localhost:8080","*")
                .allowedMethods("GET","POST","DELETE","PUT","OPTIONS")
                .allowedHeaders("*");
    }
}
