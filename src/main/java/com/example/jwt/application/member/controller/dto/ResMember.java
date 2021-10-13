package com.example.jwt.application.member.controller.dto;


import com.example.jwt.application.member.domain.Member;
import lombok.Getter;

@Getter
public class ResMember {

    private final String email;
    private final String password;
    private final String username;

    public ResMember(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static ResMember from(Member member) {
        return new ResMember(member.getEmail(), member.getPassword(), member.getUsername());
    }

}
