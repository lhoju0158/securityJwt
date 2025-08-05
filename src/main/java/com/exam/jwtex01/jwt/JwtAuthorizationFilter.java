package com.exam.jwtex01.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.exam.jwtex01.config.auth.PrincipalDetails;
import com.exam.jwtex01.model.User;
import com.exam.jwtex01.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// Security가 filter를 가지고 있는데 그 중 BasicAuthenticationFilter가 있음
// 권한, 인증이 필요하면 해당 필터를 무조건 탄다, 만일 아니면 이거 안탐
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
        super(authManager);
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        // JWT 토큰 검증과정을 거쳐서 정상적인 사용자인지 확인이 필요함
        String jwtHeader = request.getHeader("Authorization");

        //header가 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, "");
        String username =
            JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();
        if(username!=null){
            // 서명이 정상적으로 불러와짐
            User user = userRepository.findByUsername(username); // 사용자 불러오기
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            // authentication 객체를 강제로 만든다.
            // username이 null이 아니기 때문에 만들 수 있음
            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

            // 강제로 Security session에 접근해서 Authentication 객체를저
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
