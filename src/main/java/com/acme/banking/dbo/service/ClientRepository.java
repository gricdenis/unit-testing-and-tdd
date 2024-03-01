package com.acme.banking.dbo.service;

import com.acme.banking.dbo.domain.Client;

public interface ClientRepository {
    void save(Client client);

    int generateId();

    Client getById(int id);
}
