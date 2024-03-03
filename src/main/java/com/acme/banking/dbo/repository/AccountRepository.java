package com.acme.banking.dbo.repository;

import com.acme.banking.dbo.domain.Account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> getById(int id);

    void save(Account account);
}
