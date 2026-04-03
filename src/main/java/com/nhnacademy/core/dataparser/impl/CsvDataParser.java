package com.nhnacademy.core.dataparser.impl;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.dataparser.DataParser;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.repository.PriceRepository;
import com.nhnacademy.core.properties.FileProperties;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
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
    private final FileProperties fileProperties;
    private final ResourceLoader resourceLoader;
    private final PriceRepository priceRepository;
    private final AccountRepository accountRepository;

    @PostConstruct
    @Override
    public void init() {
        priceRepository.saveAll(load(fileProperties.getPricePath(), Price.class));
        accountRepository.saveAll(load(fileProperties.getAccountPath(), Account.class));
    }

    private <T> List<T> load(String path, Class<T> clazz) {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        try(Reader reader = new InputStreamReader(
                resource.getInputStream(), StandardCharsets.UTF_8)) {

            List<T> result = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .withSkipLines(1)
                    .build()
                    .parse();

            log.info("CSV {} 로드 완료: {}건", clazz.getSimpleName(), result.size());
            return result;
        } catch (IOException e) {
            log.error("CSV 파싱 실패: {}", path, e);
            return Collections.emptyList();
        }
    }
}
