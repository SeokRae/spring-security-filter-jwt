package com.example.jwt.application.member.controller.dto;

import lombok.Getter;

import java.util.Optional;

@Getter
public class ReqUpdateMember {

    private final String password;
    private final String username;

    public ReqUpdateMember(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public Optional<String> isPassword() {
        return Optional.of(password);
    }

    public Optional<String> isUsername() {
        return Optional.of(username);
    }
}
