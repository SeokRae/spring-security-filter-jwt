package com.example.jwt.application.member.domain;

import lombok.Generated;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID", nullable = false)
    private Long id;

    private String email;

    private String password;

    private String username;

    private Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member of(String email, String password) {
        return new Member(email, password);
    }

    public Member update() {
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
