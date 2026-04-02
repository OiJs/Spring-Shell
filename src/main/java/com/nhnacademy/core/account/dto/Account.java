package com.nhnacademy.core.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @JsonProperty("아이디")
    private Long id;

    @JsonProperty("비밀번호")
    private String pwd;

    @JsonProperty("이름")
    private String name;
}
