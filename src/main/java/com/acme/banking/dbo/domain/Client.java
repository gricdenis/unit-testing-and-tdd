package com.acme.banking.dbo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class Client {
    private final int id;
    private final String name;
    private final Collection<Account> accounts = new ArrayList<>();

    public Client(int id, String name) {
        if (id <= 0) throw new IllegalArgumentException("Bad ID");
        if ("".equals(name) || name == null) throw new IllegalArgumentException("Empty client name");
//        || name.isEmpty()

        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
    }

    public void addAccount(Account account) {
        if (account.getClient() != this) {
            throw new IllegalStateException("Another client has this account");
        }
        this.accounts.add(account);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(accounts, client.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, accounts);
    }
}
