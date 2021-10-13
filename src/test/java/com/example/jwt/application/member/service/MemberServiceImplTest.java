package com.example.jwt.application.member.service;

import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ResMember;
import com.example.jwt.application.member.domain.repository.MemberRepository;
import com.example.jwt.application.member.exception.DuplicateMemberException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @DisplayName("사용자 등록 서비스 테스트")
    @Test
    void when_save_member_expect_success() {
        ReqSaveMember reqSaveMember = new ReqSaveMember("seokrae@gmail.com", "1234");

        ResMember resMember = memberService.signUpMember(reqSaveMember);

        assertThat(reqSaveMember.getEmail()).isEqualTo(resMember.getEmail());
    }

    @DisplayName("사용자 등록 서비스 실패(중복 사용자) 테스트")
    @Test
    void when_save_member_expect_fail_duplicate_member() {
        ReqSaveMember reqSaveMember = new ReqSaveMember("seokrae@gmail.com", "1234");

        memberService.signUpMember(reqSaveMember);

        assertThatExceptionOfType(DuplicateMemberException.class)
                .isThrownBy(() -> memberService.signUpMember(reqSaveMember));
    }
}