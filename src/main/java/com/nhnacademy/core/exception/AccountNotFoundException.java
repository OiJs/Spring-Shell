package com.nhnacademy.core.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("존재하지 않는 계정입니다: " + id);
    }
}