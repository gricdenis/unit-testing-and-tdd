package com.acme.banking.dbo;

import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.domain.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientInitialTest {

    Client sut;
    final int validId = 1;
    final String validName = "Test Testovich";
    @BeforeEach
    void setUp(){
        sut = new Client(validId, validName);
    }

    @Test
    void shouldNotCreateWhenNonValidID() {
        //region given
        var invalidId = 0;
        //end region

        //region then
        assertThrows(IllegalArgumentException.class, () -> new Client(invalidId, validName));
        //end region

    }

    @Test
    void shouldNotCreateWhenNonValidName() {
        var invalidName = "";

        assertThrows(IllegalArgumentException.class, () -> new Client(validId, invalidName));
    }

    @Test
    void shouldNotCreateWhenNameIsNull() {
        String invalidName = null;

        assertThrows(IllegalArgumentException.class, () -> new Client(validId, invalidName));
    }

    @Test
    void shouldSaveWhenAllFieldAreValid(){
        assertAll(
                () -> assertEquals(validId, sut.getId()),
                () -> assertEquals(validName, sut.getName())
        );
    }

    @Test
    void shouldSaveAccountWhenAccountIsNotBinded() {
        int accountId = 123;
        double amount = 1D;
        SavingAccount account = new SavingAccount(accountId, sut, amount);
        sut.addAccount(account);

        boolean isSaved = sut.getAccounts().stream()
                .anyMatch(a -> a.getId() == accountId);

        assertTrue(isSaved);

    }

    @Test
    void shouldNotAddAccountWhenAccountIsBinded() {
        int accountId = 123;
        double amount = 1D;
        Client actualOwner = new Client(validId, validName);
        SavingAccount account = new SavingAccount(accountId, actualOwner, amount);

//        actualOwner.addAccount(account);

        assertThrows(IllegalStateException.class, () -> sut.addAccount(account));
    }
}
