package com.example.jwt.application.member.domain;

import com.example.jwt.application.member.controller.dto.ReqUpdateMember;
import lombok.AccessLevel;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String username;

    private Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private Member(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Member from(Member member) {
        return new Member(member.getEmail(), member.getPassword(), member.getUsername());
    }

    public static Member of(String email, String password) {
        return new Member(email, password);
    }

    public Member encode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
        return this;
    }

    public Member update(PasswordEncoder passwordEncoder, ReqUpdateMember updateMember) {
        updateMember.isUsername()
                .ifPresent(updatedUsername -> this.username = updatedUsername);
        updateMember.isPassword()
                .ifPresent(updatedPassword -> this.password = passwordEncoder.encode(updatedPassword));
        return this;
    }

    @Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(email, member.email);
    }

    @Generated
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
