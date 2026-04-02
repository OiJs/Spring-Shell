package com.nhnacademy.core.price.repository;

import com.nhnacademy.core.price.dto.Price;
import java.util.List;

public interface PriceRepository {
    void saveAll(List<Price> prices);
    List<String> cities();
    List<String> sectors(String city);
    Price price(String city, String sector);
}
