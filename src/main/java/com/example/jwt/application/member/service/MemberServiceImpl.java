package com.example.jwt.application.member.service;

import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ResMember;
import com.example.jwt.application.member.domain.Member;
import com.example.jwt.application.member.domain.repository.MemberRepository;
import com.example.jwt.application.member.exception.DuplicateMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public ResMember signUpMember(ReqSaveMember reqSaveMember) {
        boolean existsMemberByEmail = memberRepository.existsMemberByEmail(reqSaveMember.getEmail());
        if (existsMemberByEmail) {
            throw new DuplicateMemberException("이미 존재하는 사용자");
        }
        Member member = Member.of(reqSaveMember.getEmail(), reqSaveMember.getPassword()).encode(passwordEncoder);
        return ResMember.of(memberRepository.save(member));
    }
}
