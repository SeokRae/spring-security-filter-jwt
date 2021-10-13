package com.example.jwt.application.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @DisplayName("고객 정보 생성 테스트")
    @Test
    void when_create_member_expect_success() {
        Member actual = Member.of("seokrae@gmail.com", "1234");

        assertThat(actual).isEqualTo(Member.of("seokrae@gmail.com", "1111"));
    }

    @DisplayName("고객 정보 생성 및 패스워드 인코딩 테스트")
    @Test
    void when_create_member_and_encode_expect_success() {
        Member actual = Member.of("seokrae@gmail.com", "1234");

        actual.encode(passwordEncoder);

        boolean matches = passwordEncoder.matches("1234", actual.getPassword());

        assertThat(matches).isTrue();
    }

    @DisplayName("고객 정보 수정 사용자명 수정 테스트")
    @Test
    void when_update_member_expect_success() {
        Member actual = Member.of("seokrae@gmail.com", "1234");

        actual.update("seokrae");

        assertThat(actual.getUsername()).isEqualTo("seokrae");
    }

    @DisplayName("고객 정보 수정 사용자명 수정 실패 테스트")
    @Test
    void when_update_member_expect_fail() {
        Member actual = Member.of("seokrae@gmail.com", "1234");

        actual = actual.update("");

        assertThat(actual.getUsername()).isBlank();
    }
}