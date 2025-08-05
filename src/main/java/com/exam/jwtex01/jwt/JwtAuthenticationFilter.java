package com.exam.jwtex01.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// usernamePasswordAuthenticationFilter를 상속하면,
// spring security에서 UsernamePasswordAuthenticationFilter가 존재한다.
// 해당 필터를 extends한다.
// /login으로 요청해서 username, password를 전송하면
// UsernamePasswordAuthenticationFilter가 동작한다.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        System.out.println("로그인 시도");
        return super.attemptAuthentication(request,response);
    }
}
