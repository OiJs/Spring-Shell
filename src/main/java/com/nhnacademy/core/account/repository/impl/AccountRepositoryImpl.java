package com.nhnacademy.core.account.repository.impl;

import com.nhnacademy.core.account.dto.Account;
import com.nhnacademy.core.account.repository.AccountRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final List<Account> store = new ArrayList<>();

    @Override
    public void saveAll(List<Account> accounts) {
        store.addAll(accounts);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return store.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }
}
