package com.acme.banking.dbo.domain;

import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;

public class Branch {

    public String name = "Leninskiy";
    private Collection<Account> accounts;

    public Branch(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    public Collection<Account> getAccounts() {
        return unmodifiableCollection(accounts);
    }

    public Collection<Branch> getChildren() {
        return null;
    }

    public String toReport(){
        if (getName().isEmpty()) throw new IllegalArgumentException();

        return "# " + this.getName() + this.getSum();
    }

    private String getSum() {
        return String.valueOf(100000.0);
    }

    public String getName() {
        return name;
    }
}
