package com.sii.eucaptcha.configuration.filter;

import com.sii.eucaptcha.security.JwtToken;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HeaderFilterAdder extends OncePerRequestFilter {
    private final JwtToken jwtToken;

    public HeaderFilterAdder(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getRequestURI().contains("captchaImg")){
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-jwtstring");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "x-jwtString");
            httpServletResponse.setHeader("x-jwtString" , jwtToken.generateJwtToken());
        }
        if(httpServletRequest.getRequestURI().contains("reloadCaptchaImg")){
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "X-Requested-With");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-jwtstring");
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "x-jwtString");
            httpServletResponse.setHeader("x-jwtString" ,httpServletRequest.getHeader("x-jwtString") );
        }
        filterChain.doFilter(httpServletRequest , httpServletResponse);
    }
}