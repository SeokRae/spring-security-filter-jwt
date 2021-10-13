package com.example.jwt.core.security.service;

import com.example.jwt.application.member.domain.Member;
import com.example.jwt.application.member.domain.repository.MemberRepository;
import com.example.jwt.application.member.exception.NotFoundMemberException;
import com.example.jwt.core.security.context.UserDetailsContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(username)
                .orElseThrow(() -> new NotFoundMemberException("사용자가 존재하지 않습니다."));
        return new UserDetailsContext(member, new ArrayList<>());
    }
}
