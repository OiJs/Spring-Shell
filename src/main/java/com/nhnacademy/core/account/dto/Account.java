package com.nhnacademy.core.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nhnacademy.core.converter.StringTrimConverter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @CsvBindByPosition(position = 0)
    @JsonProperty("아이디")
    private Long id;

    @CsvCustomBindByPosition(position = 1, converter = StringTrimConverter.class)
    @JsonProperty("비밀번호")
    private String pwd;

    @CsvCustomBindByPosition(position = 2, converter = StringTrimConverter.class)
    @JsonProperty("이름")
    private String name;
}
