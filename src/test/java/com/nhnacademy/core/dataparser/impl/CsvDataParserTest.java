package com.nhnacademy.core.dataparser.impl;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.price.repository.PriceRepository;
import com.nhnacademy.core.properties.FileProperties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@ExtendWith(MockitoExtension.class)
class CsvDataParserTest {

    @Mock private FileProperties fileProperties;
    @Mock private ResourceLoader resourceLoader;
    @Mock private PriceRepository priceRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private Resource resource;

    @InjectMocks
    private CsvDataParser csvDataParser;

    @Test
    void init_success() throws IOException {
        String csvContent = "id,pwd,name\n1,123,이준서";
        when(fileProperties.getPricePath()).thenReturn("price.csv");
        when(fileProperties.getAccountPath()).thenReturn("account.csv");
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(new ByteArrayInputStream(csvContent.getBytes()));

        csvDataParser.init();

        verify(priceRepository).saveAll(anyList());
        verify(accountRepository).saveAll(anyList());
    }

    @Test
    void init_exception() throws IOException {
        when(fileProperties.getPricePath()).thenReturn("error.csv");
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        when(resource.getInputStream()).thenThrow(new IOException());

        csvDataParser.init();

        verify(priceRepository).saveAll(argThat(List::isEmpty));
    }
}