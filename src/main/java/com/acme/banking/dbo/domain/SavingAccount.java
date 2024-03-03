package com.acme.banking.dbo.domain;

import java.util.Objects;

public class SavingAccount implements Account {
    private final int id;
    private final Client client;
    private final double amount;

    public SavingAccount(int id, Client client, double amount) {
        if (id <= 0) throw new IllegalArgumentException("Bad ID");
        if (client == null) throw new IllegalArgumentException("Client is not defined");
        if (amount < 0) throw new IllegalArgumentException("Incorrect amount");

        this.id = id;
        this.client = client;
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public SavingAccount changeAmount(Account account, double amount){
        return new SavingAccount(account.getId(), account.getClient(), amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavingAccount that = (SavingAccount) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, amount);
    }
}
