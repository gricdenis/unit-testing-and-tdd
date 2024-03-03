package com.acme.banking.dbo.repository;

import com.acme.banking.dbo.domain.Client;

import java.util.Optional;

public interface ClientRepository {
    void save(Client client);

    //предполагаем что id возвращается по контракту
    int generateId();

    Optional<Client> getById(int id);
}
