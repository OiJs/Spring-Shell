package com.nhnacademy.core.account.service;

import com.nhnacademy.core.account.dto.Account;

public interface AuthenticationService {
    Account login(Long id, String pwd);
    void logout();
    Account getCurrentAccount();
    boolean isLoggedIn();
}
