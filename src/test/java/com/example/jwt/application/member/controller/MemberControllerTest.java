package com.example.jwt.application.member.controller;

import com.example.jwt.application.member.controller.dto.ReqLoginMember;
import com.example.jwt.application.member.controller.dto.ReqSaveMember;
import com.example.jwt.application.member.controller.dto.ReqUpdateMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private String token;

    @Order(1)
    @DisplayName("사용자 등록 API 테스트")
    @Test
    void when_signUp_expect_success() throws Exception {
        ReqSaveMember reqSaveMember = new ReqSaveMember("seokrae@gmail.com", "1234");

        mockMvc.perform(
                        post("/api/members")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(reqSaveMember))
                )
                .andExpect(status().isCreated());
    }

    @Order(2)
    @DisplayName("사용자 로그인 API JWT 발급 테스트")
    @Test
    void when_login_expect_success() throws Exception {
        ReqLoginMember reqLoginMember = new ReqLoginMember("seokrae@gmail.com", "1234");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/members/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(mapper.writeValueAsString(reqLoginMember))
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        token = response.getHeader(AUTHORIZATION);
    }

    @Order(3)
    @DisplayName("JWT 토큰으로 사용자 조회")
    @Test
    void when_current_member_expect_success() throws Exception {
        mockMvc.perform(
                        get("/api/members")
                                .header(AUTHORIZATION, token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("seokrae@gmail.com"));
    }

    @Order(4)
    @DisplayName("JWT 토큰으로 사용자 정보 수정 테스트")
    @Test
    void when_update_member_expect_success() throws Exception {
        ReqUpdateMember updateMember = new ReqUpdateMember("4321", "seokrae");
        mockMvc.perform(
                        put("/api/members")
                                .header(AUTHORIZATION, token)
                                .content(mapper.writeValueAsString(updateMember))
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("seokrae@gmail.com"))
                .andExpect(jsonPath("$.username").value("seokrae"));
    }
}