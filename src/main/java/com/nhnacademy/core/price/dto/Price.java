package com.nhnacademy.core.price.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Price {
    @JsonProperty("순번")
    private int sequence;

    @JsonProperty("지자체명")
    private String city;

    @JsonProperty("업종")
    private String sector;

    @JsonProperty("구간금액(원)")
    private int unitPrice;
}
