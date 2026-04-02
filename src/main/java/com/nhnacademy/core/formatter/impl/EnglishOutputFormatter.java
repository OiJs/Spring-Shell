package com.nhnacademy.core.formatter.impl;

import com.nhnacademy.core.formatter.OutPutFormatter;
import com.nhnacademy.core.price.dto.Price;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"eng", "default"})
public class EnglishOutputFormatter implements OutPutFormatter {
    @Override
    public String format(Price price, int usage) {
        long totalBill = (long) price.getUnitPrice() * usage;
        String formattedBill = String.valueOf(totalBill);

        return String.format("city: %s, sector: %s, unit price(won): %d, bill total(won): %s",
                price.getCity(),
                price.getSector(),
                price.getUnitPrice(),
                formattedBill);
    }
}
