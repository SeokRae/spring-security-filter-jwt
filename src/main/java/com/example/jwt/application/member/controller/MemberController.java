package com.example.jwt.application.member.controller;

import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ResMember;
import com.example.jwt.application.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<ResMember> signUp(@RequestBody ReqSaveMember reqSaveMember) {
        ResMember resMember = memberService.signUpMember(reqSaveMember);
        return ResponseEntity.status(HttpStatus.CREATED).body(resMember);
    }
}
