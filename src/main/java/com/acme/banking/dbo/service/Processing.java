package com.acme.banking.dbo.service;

import com.acme.banking.dbo.domain.Account;
import com.acme.banking.dbo.domain.Cash;
import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.exception.AccountNotFoundException;
import com.acme.banking.dbo.exception.ClientNotFoundException;
import com.acme.banking.dbo.exception.TransferAccountAmountException;
import com.acme.banking.dbo.repository.AccountRepository;
import com.acme.banking.dbo.repository.ClientRepository;

import java.util.Collection;
import java.util.List;

public class Processing {
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final Cash cash;


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
        Client client = clientRepository.getById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id: %d not found", clientId)));

        return client.getAccounts();
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Id from is the same Id to");
        }

        Account accountFrom = accountRepository.getById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %d not found", fromAccountId)));
        Account accountTo = accountRepository.getById(toAccountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Account with id %d not found", toAccountId)));

        if ((accountFrom.getAmount() - amount) < 0) {
            throw new TransferAccountAmountException(String.format("Account amount with id: %d less than can be transferred", fromAccountId));
        }


        accountFrom = accountFrom.changeAmount(accountFrom,accountFrom.getAmount() - amount);
        accountTo = accountTo.changeAmount(accountTo,accountTo.getAmount() + amount);

        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
    }

    public void cash(double amount, int fromAccountId) {
        cash.log(amount, fromAccountId);
    }
}
