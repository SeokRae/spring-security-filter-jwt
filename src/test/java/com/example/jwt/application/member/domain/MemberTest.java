package com.example.jwt.application.member.domain;

import com.example.jwt.application.member.controller.dto.ReqUpdateMember;
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
        ReqUpdateMember updateMember = new ReqUpdateMember("4321", "seokrae");

        actual.update(passwordEncoder, updateMember);

        assertThat(actual.getUsername()).isEqualTo("seokrae");
    }

    @DisplayName("고객 정보 수정 사용자명 수정 실패 테스트")
    @Test
    void when_update_member_expect_fail() {
        Member actual = Member.of("seokrae@gmail.com", "1234");
        ReqUpdateMember updateMember = new ReqUpdateMember("", "");

        actual = actual.update(passwordEncoder, updateMember);

        assertThat(actual.getUsername()).isBlank();
    }
}