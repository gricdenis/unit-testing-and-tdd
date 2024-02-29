package com.acme.banking.dbo.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Client {
    private final int id;
    private final String name;
    private Collection<Account> accounts = new ArrayList<>(); //TODO

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

}
