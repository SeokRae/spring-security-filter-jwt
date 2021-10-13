package com.example.jwt.application.member.service;

import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ReqUpdateMember;
import com.example.jwt.application.member.controller.dto.ResMember;
import com.example.jwt.application.member.exception.DuplicateMemberException;
import com.example.jwt.application.member.exception.NotFoundMemberException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @Order(1)
    @DisplayName("사용자 등록 서비스 테스트")
    @Test
    void when_save_member_expect_success() {
        ReqSaveMember reqSaveMember = new ReqSaveMember("seokrae@gmail.com", "1234");

        ResMember resMember = memberService.signUpMember(reqSaveMember);

        assertThat(reqSaveMember.getEmail()).isEqualTo(resMember.getEmail());
    }

    @Order(2)
    @DisplayName("사용자 조회 서비스 (존재하지 않는 사용자) 실패 테스트")
    @Test
    void when_get_member_expect_fail() {
        String email = "notFound@gmail.com";

        assertThatExceptionOfType(NotFoundMemberException.class)
                .isThrownBy(() -> memberService.findMember(email));
    }

    @Order(3)
    @DisplayName("사용자 조회 서비스 테스트")
    @Test
    void when_get_member_expect_success() {
        String email = "seokrae@gmail.com";

        ResMember member = memberService.findMember(email);

        assertThat(member.getEmail()).isEqualTo("seokrae@gmail.com");
    }

    @Order(4)
    @DisplayName("사용자 등록 서비스 실패(중복 사용자) 테스트")
    @Test
    void when_save_member_expect_fail_duplicate_member() {
        ReqSaveMember reqSaveMember = new ReqSaveMember("seokrae@gmail.com", "1234");
        assertThatExceptionOfType(DuplicateMemberException.class)
                .isThrownBy(() -> memberService.signUpMember(reqSaveMember));
    }

    @Order(5)
    @DisplayName("사용자 정보 패스워드 및 사용자명 수정 (빈 값) 테스트")
    @Test
    void when_update_member_expect_success_empty() {
        ReqUpdateMember updateMember = new ReqUpdateMember("", "");
        ResMember resMember = memberService.updateMember("seokrae@gmail.com", updateMember);

        assertThat(resMember.getUsername()).isNull();
        assertThat(passwordEncoder.matches("1234", resMember.getPassword())).isTrue();
    }

    @Order(6)
    @DisplayName("사용자 정보 패스워드 및 사용자명 수정 테스트")
    @Test
    void when_update_member_expect_success() {
        ReqUpdateMember updateMember = new ReqUpdateMember("4321", "seokrae");
        ResMember resMember = memberService.updateMember("seokrae@gmail.com", updateMember);

        assertThat(resMember.getUsername()).isEqualTo("seokrae");
        assertThat(passwordEncoder.matches("4321", resMember.getPassword())).isTrue();
    }
}