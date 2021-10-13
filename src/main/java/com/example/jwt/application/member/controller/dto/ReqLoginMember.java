package com.example.jwt.application.member.controller.dto;


import lombok.Getter;

@Getter
public class ReqLoginMember {

    private final String email;
    private final String password;

    public ReqLoginMember(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
