package com.fusionsoft.boardback.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer{
    
    @Override
    public void addCorsMappings(CorsRegistry corsResistry) {
        corsResistry.addMapping("/**")
                    .allowedMethods("*")
                    .allowedOrigins("*");
    }
}
