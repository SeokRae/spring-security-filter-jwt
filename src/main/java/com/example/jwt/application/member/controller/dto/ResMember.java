package com.example.jwt.application.member.controller.dto;


import com.example.jwt.application.member.domain.Member;
import lombok.Getter;

@Getter
public class ResMember {

    private final String email;
    private final String password;
    private final String username;
    private final String token;

    private ResMember(String email, String password, String username) {
        this(email, password, username, null);
    }

    public ResMember(String email, String password, String username, String token) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.token = token;
    }

    public static ResMember from(Member member) {
        return new ResMember(member.getEmail(), member.getPassword(), member.getUsername());
    }

    public static ResMember of(Member member, String token) {
        return new ResMember(member.getEmail(), member.getPassword(), member.getUsername(), token);
    }
}
