package com.exam.jwtex01.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class MyFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
        FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter2 doFilter");
        filterChain.doFilter(servletRequest,servletResponse); // 다시 등록을 시켜줘야 한다.
    }
}
