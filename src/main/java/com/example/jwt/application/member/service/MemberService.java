package com.example.jwt.application.member.service;

import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ResMember;

public interface MemberService {
    ResMember signUpMember(ReqSaveMember reqSaveMember);
}
