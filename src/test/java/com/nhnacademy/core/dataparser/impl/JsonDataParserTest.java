package com.nhnacademy.core.dataparser.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.price.repository.PriceRepository;
import com.nhnacademy.core.properties.FileProperties;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@ExtendWith(MockitoExtension.class)
class JsonDataParserTest {

    @Mock private ObjectMapper objectMapper;
    @Mock private FileProperties fileProperties;
    @Mock private ResourceLoader resourceLoader;
    @Mock private PriceRepository priceRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private Resource resource;

    @InjectMocks
    private JsonDataParser jsonDataParser;

    @Test
    void init_success() throws Exception {
        when(fileProperties.getPricePath()).thenReturn("price.json");
        when(fileProperties.getAccountPath()).thenReturn("account.json");
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream("[]".getBytes()));
        when(objectMapper.readValue(any(InputStream.class), any(TypeReference.class))).thenReturn(List.of());

        jsonDataParser.init();

        verify(priceRepository).saveAll(anyList());
        verify(accountRepository).saveAll(anyList());
    }

    @Test
    void init_fail() throws Exception {
        when(fileProperties.getPricePath()).thenReturn("invalid.json");
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenThrow(new RuntimeException());

        jsonDataParser.init();

        verify(priceRepository).saveAll(argThat(List::isEmpty));
    }
}