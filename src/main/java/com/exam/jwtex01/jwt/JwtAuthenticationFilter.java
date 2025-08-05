package com.exam.jwtex01.jwt;

import com.exam.jwtex01.config.auth.PrincipalDetails;
import com.exam.jwtex01.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        try{
            ObjectMapper mapper = new ObjectMapper(); // JSON 데이터 파싱해준다.
            User user = mapper.readValue(request.getInputStream(), User.class);

            // 토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 로그인 시도
            // 밑 코드가 실행될 때 PrincipalDetailsService의 loadUserByUsername 함수가 실행됨
            // DB에 있는 username과 password가 일치한다는 뜻
            Authentication auth = authenticationManager.authenticate(authenticationToken);
            // auth에는 로그인 정보가 담긴다.
            PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
            System.out.println("principalDetails = " + principalDetails.getUser().getUsername());
            return auth; // session에 저장됨

        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
