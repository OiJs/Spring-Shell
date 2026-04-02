package com.nhnacademy.core.formatter.impl;

import com.nhnacademy.core.formatter.OutPutFormatter;
import com.nhnacademy.core.price.dto.Price;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("kor")
public class KoreanOutputFormatter implements OutPutFormatter {
    @Override
    public String format(Price price, int usage) {
        int totalBill = price.getUnitPrice() * usage;
        String formattedBill = String.valueOf(totalBill);

        return String.format("지자체명: %s, 업종: %s, 구간금액(원): %d, 총금액(원): %s",
                price.getCity(),
                price.getSector(),
                price.getUnitPrice(),
                formattedBill);
    }
}
