package com.nhnacademy.core.formatter;

import com.nhnacademy.core.price.dto.Price;

public interface OutPutFormatter {
    String format(Price price, int usage);
}
