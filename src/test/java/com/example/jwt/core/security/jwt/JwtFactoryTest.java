package com.example.jwt.core.security.jwt;

import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class JwtFactoryTest {

    @Autowired
    private JwtFactory jwtFactory;
    private String token;

    @Order(1)
    @DisplayName("JWT 토큰 발급 테스트")
    @Test
    void when_generate_token_expect_success() {
        token = jwtFactory.generateToken("seokrae@gmail.com", 1);

        assertThat(token).isNotEmpty();
    }

    @Order(2)
    @DisplayName("JWT 토큰 내에 이메일 추출 테스트")
    @Test
    void when_extract_email_by_token_expect_success() {
        String email = jwtFactory.extractEmail(token);

        assertThat(email).isEqualTo("seokrae@gmail.com");
    }

    @Order(3)
    @DisplayName("JWT 토큰 유효성 검사 테스트")
    @Test
    void when_validate_token_expect_success() {
        boolean isValidToken = jwtFactory.isValidToken(this.token);

        assertThat(isValidToken).isTrue();
    }

    @Order(4)
    @DisplayName("JWT 토큰 예외(잘못된 토큰) 테스트")
    @Test
    void when_isValidToken_expect_fail_jwt_exception() {
        boolean validToken = jwtFactory.isValidToken(this.token + "error");
        assertThat(validToken).isFalse();
    }

    @Order(5)
    @DisplayName("JWT 토큰 예외(NullPointerException) 테스트")
    @Test
    void when_isValidToken_expect_fail_null_exception() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> jwtFactory.isValidToken(null));
    }
}