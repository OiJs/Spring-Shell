package com.nhnacademy.core.account.repository;

import com.nhnacademy.core.account.dto.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    void saveAll(List<Account> accounts);
    Optional<Account> findById(Long id);
}