package com.nhnacademy.core.dataparser.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.dataparser.DataParser;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.repository.PriceRepository;
import com.nhnacademy.core.properties.FileProperties;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
@ConditionalOnProperty(name = "file.type", havingValue = "json")
public class JsonDataParser implements DataParser {

    private final ObjectMapper objectMapper;
    private final FileProperties fileProperties;
    private final ResourceLoader resourceLoader;
    private final PriceRepository priceRepository;
    private final AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        //Tariff
        Resource priceResource = resourceLoader.getResource(
                "classpath:" + fileProperties.getPricePath()
        );
        List<Price> prices = parse(priceResource, new TypeReference<List<Price>>() {});
        priceRepository.saveAll(prices);
        log.info("JSON Price 로드 완료: {}건", prices.size());

        //Account
        Resource accountResource = resourceLoader.getResource(
                "classpath:" + fileProperties.getAccountPath()
        );
        List<Account> accounts = parse(accountResource, new TypeReference<List<Account>>() {});
        accountRepository.saveAll(accounts);
        log.info("JSON Account 로드 완료: {}건", accounts.size());
    }

    private <T> List<T> parse(Resource resource, TypeReference<List<T>> typeReference) {
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (Exception e) {
            log.error("JSON 파싱 실패: {}", resource.getFilename(), e);
            return Collections.emptyList();
        }
    }
}