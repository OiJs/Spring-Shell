package com.nhnacademy.core.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.service.AuthenticationService;
import com.nhnacademy.core.price.dto.Price;
import com.nhnacademy.core.price.service.PriceService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyCommandsTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private MyCommands myCommands;

    @Test
    void login_alreadyLoggedIn() {
        Account account = new Account(1L, "pwd123", "이준서");
        when(authenticationService.isLoggedIn()).thenReturn(true);
        when(authenticationService.getCurrentAccount()).thenReturn(account);

        String result = myCommands.login(1L, "pwd123");

        assertTrue(result.contains("이미 이준서님으로 로그인 되어있습니다."));
    }

    @Test
    void login_success() {
        Account account = new Account(1L, "pwd123", "이준서");
        when(authenticationService.isLoggedIn()).thenReturn(false);
        when(authenticationService.login(1L, "pwd123")).thenReturn(account);

        String result = myCommands.login(1L, "pwd123");

        assertEquals("Account(id = 1, name = pwd123, name = 이준서)", result);
    }

    @Test
    void logout() {
        String result = myCommands.logout();

        verify(authenticationService).logout();
        assertEquals("good bye", result);
    }

    @Test
    void currentUser() {
        Account account = new Account(1L, "pwd123", "이준서");
        when(authenticationService.getCurrentAccount()).thenReturn(account);

        String result = myCommands.currentUser();

        assertEquals("Account(id = 1, name = pwd123, name = 이준서)", result);
    }

    @Test
    void city() {
        List<String> cities = List.of("Gwangju", "Seoul");
        when(priceService.cities()).thenReturn(cities);

        String result = myCommands.city();

        assertEquals(cities.toString(), result);
    }

    @Test
    void billTotal() {
        when(priceService.billTotal("Gwangju", "Home", 100)).thenReturn("15000");

        String result = myCommands.billTotal("Gwangju", "Home", 100);

        assertEquals("15000", result);
    }

    @Test
    void sector_exists() {
        List<String> sectors = List.of("Home", "Business");
        when(priceService.sectors("Gwangju")).thenReturn(sectors);

        String result = myCommands.sector("Gwangju");

        assertEquals(sectors.toString(), result);
    }

    @Test
    void sector_empty() {
        when(priceService.sectors("Unknown")).thenReturn(Collections.emptyList());

        String result = myCommands.sector("Unknown");

        assertEquals("해당 지자체(Unknown)의 업종 정보를 찾을 수 없습니다.", result);
    }

    @Test
    void price() {
        Price price = new Price(1, "Gwangju", "Home", 150);
        when(priceService.price("Gwangju", "Home")).thenReturn(price);

        String result = myCommands.price("Gwangju", "Home");

        assertEquals("Price(id=1, city=Gwangju, sector=Home, unitPrice=150", result);
    }
}