package com.nhnacademy.core.price.repository.impl;

import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.repository.PriceRepository;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;


@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final Map<String, List<Price>> store = new ConcurrentHashMap<>();

    @Override
    public void saveAll(List<Price> prices) {
        store.putAll(
                prices.stream()
                        .filter(p -> Objects.nonNull(p.getCity()) && Objects.nonNull(p.getSector()))
                        .collect(Collectors.groupingBy(Price::getCity))
        );
    }

    @Override
    public List<String> cities() {
        return store.keySet().stream().sorted().toList();
    }

    @Override
    public List<String> sectors(String city) {
        return store.getOrDefault(city, Collections.emptyList())
                .stream()
                .map(Price::getSector)
                .distinct()
                .sorted()
                .toList();
    }

    @Override
    public Price price(String city, String sector) {
        return store.getOrDefault(city, Collections.emptyList())
                .stream()
                .filter(p -> p.getSector().equals(sector))
                .findFirst()
                .orElse(null);
    }
}