package com.exam.jwtex01.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest; // 다운케스팅
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if(req.getMethod().equals("POST")){
            System.out.println("POST 요청됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            System.out.println("Filter3");
            if(headerAuth.equals("cos")) {
                filterChain.doFilter(req, res);
            } else{
                PrintWriter out = res.getWriter();
                out.println("인증 안됨");
            }
        }
    }
}
