package com.example.jwt.core.security.filter;

import com.example.jwt.application.member.controller.dto.ReqLoginMember;
import com.example.jwt.core.security.jwt.JwtFactory;
import com.example.jwt.core.security.token.JwtAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtFactory jwtFactory;
    private final ObjectMapper mapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtFactory jwtFactory, ObjectMapper mapper) {
        super(authenticationManager);
        this.jwtFactory = jwtFactory;
        this.mapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ReqLoginMember reqLoginMember = mapper.readValue(request.getReader(), ReqLoginMember.class);
            JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(reqLoginMember.getEmail(), reqLoginMember.getPassword());
            return super.getAuthenticationManager().authenticate(jwtAuthenticationToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException("로그인 인증 처리 실패 예외");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String principal = (String) authResult.getPrincipal();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtFactory.generateToken(principal, 1));
        response.getWriter();
    }
}
