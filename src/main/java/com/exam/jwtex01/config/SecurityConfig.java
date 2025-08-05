package com.exam.jwtex01.config;

import com.exam.jwtex01.filter.MyFilter1;
import com.exam.jwtex01.filter.MyFilter3;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // session을 사용하지 않겠다. (stateless server)
            .formLogin(AbstractHttpConfigurer::disable) // jwt 쓸거니깐 formLogin 안함
            .httpBasic(AbstractHttpConfigurer::disable) // 기본적인 Http 형식도 사용하지 않는다.
            .addFilter(corsFilter) // 인증이 필요한 경우는 filer에 등록을 해야한다.
            .authorizeHttpRequests(auth -> auth // role에 따른 접근 권한 주기
                .requestMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .requestMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
            );

        return http.build();
    }
}
