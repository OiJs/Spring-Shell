package com.nhnacademy.core.account.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.exception.AccountNotFoundException;
import com.nhnacademy.core.exception.InvalidPasswordException;
import com.nhnacademy.core.exception.NotLoginException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @Test
    void loginSuccessTest(){
        Account account = new Account(1L, "1", "test");

        when(repository.findById(any())).thenReturn(Optional.of(account));

        Account result = authenticationService.login(1L, "1");

        Assertions.assertEquals(account, result);
    }

    @Test
    void loginFailTest_accountNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class,
                () -> authenticationService.login(1L, "1"));
    }

    @Test
    void loginFailTest_wrongPassword() {
        Account account = new Account(1L, "1234", "test");

        when(repository.findById(any())).thenReturn(Optional.of(account));

        Assertions.assertThrows(InvalidPasswordException.class, () -> authenticationService.login(1L, "1"));
    }

    @Test
    void currentAccountTest() {
        Account account = new Account(1L, "1", "test");

        when(repository.findById(any())).thenReturn(Optional.of(account));

        authenticationService.login(1L, "1");

        Account currentAccount = authenticationService.getCurrentAccount();
        Assertions.assertEquals(account, currentAccount);
    }

    @Test
    void getCurrentAccountFailTest_notLoggedIn() {
        Assertions.assertThrows(NotLoginException.class,
                () -> authenticationService.getCurrentAccount());
    }

    @Test
    void logoutSuccessTest() {
        Account account = new Account(1L, "1", "test");
        when(repository.findById(any())).thenReturn(Optional.of(account));
        authenticationService.login(1L, "1");

        authenticationService.logout();

        Assertions.assertFalse(authenticationService.isLoggedIn());
    }

    @Test
    void logoutFailTest_notLoggedIn() {
        Assertions.assertThrows(NotLoginException.class,
                () -> authenticationService.logout());
    }

    @Test
    void isLoggedInTest() {
        Assertions.assertFalse(authenticationService.isLoggedIn());

        Account account = new Account(1L, "1", "test");
        when(repository.findById(any())).thenReturn(Optional.of(account));
        authenticationService.login(1L, "1");

        Assertions.assertTrue(authenticationService.isLoggedIn());
    }
}
