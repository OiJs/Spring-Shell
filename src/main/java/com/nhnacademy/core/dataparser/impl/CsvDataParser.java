package com.nhnacademy.core.dataparser.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.dataparser.DataParser;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.repository.PriceRepository;
import com.nhnacademy.core.properties.FileProperties;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "file.type", havingValue = "csv")
public class CsvDataParser implements DataParser {
    private final ObjectMapper objectMapper;
    private final FileProperties fileProperties;
    private final ResourceLoader resourceLoader;
    private final PriceRepository priceRepository;
    private final AccountRepository accountRepository;

    @PostConstruct
    @Override
    public void init() {
        Resource priceResource = resourceLoader.getResource(
                "classpath:" + fileProperties.getPricePath()
        );
        List<Price> prices = parse(priceResource, Price.class);
        priceRepository.saveAll(prices);
        log.info("CSV Price 로드 완료: {}건", prices.size());

        Resource accountResource = resourceLoader.getResource(
                "classpath:" + fileProperties.getAccountPath()
        );
        List<Account> accounts = parse(accountResource, Account.class);
        accountRepository.saveAll(accounts);
        log.info("CSV Account 로드 완료: {}건", accounts.size());    }

    private <T> List<T> parse(Resource resource, Class<T> clazz) {
        List<T> result = new ArrayList<>();

        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))
                .build()) {

            String[] header = reader.readNext();

            for (int i = 0; i < header.length; i++) {
                header[i] = header[i].trim();
            }

            String[] line;
            while ((line = reader.readNext()) != null) {
                // 헤더-값 Map 생성
                Map<String, String> rowMap = new LinkedHashMap<>();
                for (int i = 0; i < header.length; i++) {
                    rowMap.put(header[i], line[i].trim());
                }
                // Map → JSON → T
                String json = objectMapper.writeValueAsString(rowMap);
                result.add(objectMapper.readValue(json, clazz));
            }

        } catch (CsvValidationException | IOException e) {
            log.error("CSV 파싱 실패: {}", resource.getFilename(), e);
        }
        return result;
    }
}
