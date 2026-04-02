package com.nhnacademy.core.price.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.service.AuthenticationService;
import com.nhnacademy.core.exception.NotLoginException;
import com.nhnacademy.core.exception.TariffNotFoundException;
import com.nhnacademy.core.formatter.OutPutFormatter;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.repository.PriceRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    AuthenticationService authenticationService;

    @Mock
    OutPutFormatter outPutFormatter;

    @InjectMocks
    PriceServiceImpl priceService;

    Account account;
    Price price;

    @BeforeEach
    void setUp() {
        account = new Account(1L, "1", "test");
        price = new Price(1, "동두천시", "가정용", 690);
    }

    @Test
    void citiesSuccessTest() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(priceRepository.cities()).thenReturn(List.of("동두천시", "파주시"));

        List<String> cities = priceService.cities();

        Assertions.assertEquals(2, cities.size());
    }

    @Test
    void citiesFailTest_notLoggedIn() {
        when(authenticationService.getCurrentAccount()).thenThrow(NotLoginException.class);
        Assertions.assertThrows(NotLoginException.class, () -> priceService.cities());
    }

    @Test
    void sectorsSuccessTest() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(priceRepository.sectors("동두천시")).thenReturn(List.of("가정용", "일반용"));

        List<String> sectors = priceService.sectors("동두천시");

        Assertions.assertTrue(sectors.contains("가정용"));
        Assertions.assertTrue(sectors.contains("일반용"));
    }

    @Test
    void sectorsFailTest_cityIsNull() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> priceService.sectors(null));
    }
    @Test
    void priceSuccessTest() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(priceRepository.price("동두천시", "가정용")).thenReturn(price);

        Price result = priceService.price("동두천시", "가정용");

        Assertions.assertEquals(price, result);
    }

    @Test
    void priceFailTest_notFound() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(priceRepository.price(any(), any())).thenReturn(null);

        Assertions.assertThrows(TariffNotFoundException.class,
                () -> priceService.price("동두천시", "가정용"));
    }

    @Test
    void billTotalSuccessTest() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(priceRepository.price("동두천시", "가정용")).thenReturn(price);
        when(outPutFormatter.format(price, 10)).thenReturn("지자체명: 동두천시, 총금액: 6900원");

        String result = priceService.billTotal("동두천시", "가정용", 10);

        Assertions.assertTrue(result.contains("6900"));
    }

    @Test
    void billTotalFailTest_notFound() {
        when(authenticationService.getCurrentAccount()).thenReturn(account);
        when(priceRepository.price(any(), any())).thenReturn(null);

        Assertions.assertThrows(TariffNotFoundException.class,
                () -> priceService.billTotal("동두천시", "가정용", 10));
    }
}
