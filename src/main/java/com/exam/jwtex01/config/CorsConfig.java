package com.exam.jwtex01.config;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할 지 설정
        config.addAllowedOrigin("*"); // 모든 ip의 응답을 허용하겠다.
        config.addAllowedHeader("*"); // 모든 header의 응답을 허용하겠다.
        config.addAllowedMethod("*"); // 모든 메소드 허용하겠다
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
