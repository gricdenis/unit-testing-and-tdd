package com.acme.banking.dbo.service;

import com.acme.banking.dbo.domain.Account;

public interface AccountRepository {
    Account getById(int id);

    void save(Account account);
}
