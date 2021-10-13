package com.example.jwt.application.member.controller.dto;

import lombok.Getter;

@Getter
public class ReqSaveMember {

    private final String email;
    private final String password;

    public ReqSaveMember(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
