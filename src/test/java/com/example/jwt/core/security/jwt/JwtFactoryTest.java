package com.example.jwt.core.security.jwt;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

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
        UserDetails userDetails = new User("seokrae@gmail.com", "1234", new ArrayList<>());

        boolean isValidToken = jwtFactory.isToken(this.token, userDetails);

        assertThat(isValidToken).isTrue();
    }
}