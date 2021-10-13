package com.example.jwt.application.member.controller;

import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ReqUpdateMember;
import com.example.jwt.application.member.controller.dto.ResMember;
import com.example.jwt.application.member.service.MemberService;
import com.example.jwt.core.security.context.UserDetailsContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<ResMember> currentMember(@AuthenticationPrincipal UserDetailsContext userDetailsContext) {
        ResMember resMember = memberService.findMember(userDetailsContext.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(resMember);
    }

    @PutMapping
    public ResponseEntity<ResMember> update(@AuthenticationPrincipal UserDetailsContext userDetailsContext, @RequestBody ReqUpdateMember updateMember) {
        ResMember resMember = memberService.updateMember(userDetailsContext.getUsername(), updateMember);
        return ResponseEntity.status(HttpStatus.OK).body(resMember);
    }
}
