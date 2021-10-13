package com.example.jwt.application.member.controller.dto;

import lombok.Getter;

@Getter
public class ReqUpdateMember {

    private final String password;
    private final String username;

    public ReqUpdateMember(String password, String username) {
        this.password = password;
        this.username = username;
    }
}
