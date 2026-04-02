package com.nhnacademy.core.account.service.impl;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.repository.AccountRepository;
import com.nhnacademy.core.account.service.AuthenticationService;
import com.nhnacademy.core.exception.AccountNotFoundException;
import com.nhnacademy.core.exception.InvalidPasswordException;

import com.nhnacademy.core.exception.NotLoginException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private Account currentAccount;

    @Override
    public Account login(Long id, String pwd) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        if (!account.getPwd().equals(pwd)) {
            log.warn("비밀번호 불일치 - id: {}", id);
            throw new InvalidPasswordException();
        }

        currentAccount = account;
        log.info("로그인 성공 - id: {}, name: {}", id, account.getName());
        return account;
    }

    @Override
    public void logout() {
        if (currentAccount == null) {
            throw new RuntimeException("로그인 상태가 아닙니다.");
        }
        log.info("로그아웃 - id: {}, name: {}", currentAccount.getId(), currentAccount.getName());
        currentAccount = null;
    }

    @Override
    public Account getCurrentAccount() {
        if (currentAccount == null) {
            throw new NotLoginException();
        }
        return currentAccount;
    }

    @Override
    public boolean isLoggedIn() {
        return Objects.nonNull(currentAccount);
    }
}