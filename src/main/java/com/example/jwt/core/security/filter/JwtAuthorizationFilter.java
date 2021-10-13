package com.example.jwt.core.security.filter;

import com.example.jwt.core.security.jwt.JwtFactory;
import com.example.jwt.core.security.token.JwtAuthenticationToken;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final JwtFactory jwtFactory;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtFactory jwtFactory) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwtFactory = jwtFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Strings.isEmpty(header)) {
            chain.doFilter(request, response);
            return;
        }

        if(jwtFactory.isValidToken(header)) {
            String email = jwtFactory.extractEmail(header);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (Objects.nonNull(userDetails)) {
                JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
                chain.doFilter(request, response);
            }
        }
    }
}
