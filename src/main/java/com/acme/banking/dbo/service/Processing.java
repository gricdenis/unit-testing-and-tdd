package com.acme.banking.dbo.service;

import com.acme.banking.dbo.domain.Account;
import com.acme.banking.dbo.domain.Cash;
import com.acme.banking.dbo.domain.Client;

import java.util.Collection;

public class Processing {
    private ClientRepository clientRepository;
    private AccountRepository accountRepository;

    private Cash cash;


    public Processing(ClientRepository clientRepository, AccountRepository accountRepository, Cash cash) {
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.cash = cash;
    }

    public Client createClient(String name) {
        int id = clientRepository.generateId();
        Client client = new Client(id, name);

        clientRepository.save(client);

        return client;
    }

    public Collection<Account> getAccountsByClientId(int clientId) {
        Client client = clientRepository.getById(clientId);

        return client.getAccounts();
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        Account accountFrom = accountRepository.getById(fromAccountId);
        Account accountTo = accountRepository.getById(toAccountId);

        accountFrom = accountFrom.changeAmount(accountFrom,accountFrom.getAmount() - amount);
        accountTo = accountTo.changeAmount(accountTo,accountTo.getAmount() + amount);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }

    public void cash(double amount, int fromAccountId) {
        cash.log(amount, fromAccountId);
    }
}
