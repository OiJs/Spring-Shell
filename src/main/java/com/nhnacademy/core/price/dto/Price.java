package com.nhnacademy.core.price.dto;

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
@Setter
@Getter
public class Price {

    @CsvBindByPosition(position = 0)
    @JsonProperty("순번")
    private int sequence;

    @CsvCustomBindByPosition(position = 1, converter = StringTrimConverter.class)
    @JsonProperty("지자체명")
    private String city;

    @CsvCustomBindByPosition(position = 2, converter = StringTrimConverter.class)
    @JsonProperty("업종")
    private String sector;

    @CsvBindByPosition(position = 6)
    @JsonProperty("구간금액(원)")
    private int unitPrice;
}
