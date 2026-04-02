package com.nhnacademy.core.price.service;

import com.nhnacademy.core.price.dto.Price;
import java.util.List;

public interface PriceService {
    List<String> sectors(String city);
    String billTotal(String city, String sector, int bill);
    Price price(String city, String sector);
    List<String> cities();
}
