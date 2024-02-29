package com.acme.banking.dbo;

import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.domain.SavingAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;


public class SavingAccountInitialTest {

    SavingAccount sut;
    Client validClient;
    final int validId = 1;
    final String validName = "Test Testovich";

    final Double validAmount = 0D;

    @BeforeEach
    void setUpClient(){
        validClient = new Client(validId, validName);
    }
    @BeforeEach
    void setUpSavingAccount(){
        sut = new SavingAccount(validId, validClient, validAmount);
    }

    @Test
    void shouldNotCreateWhenNonValidID() {
        var invalidId = 0;

        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(invalidId, validClient, validAmount));
    }

    @Test
    void shouldNotCreateWhenNonValidClient() {
//        var invalidClient = null;

        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(validId, null, validAmount));
    }

    @Test
    void shouldNotCreateWhenNonValidAmount() {
        var invalidAmount = -0.01D;

        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(validId, validClient, invalidAmount));
    }

    @Test
    void shouldCreateWhenValidAmount() {
        var validAmount = 0D;

        assertAll("Client store its properties",
                () -> assertEquals(validId, sut.getId()),
                () -> assertEquals(validClient, sut.getClient()),
                () -> assertEquals(validAmount, sut.getAmount())
        );
//        assertThrows(IllegalArgumentException.class, () -> new SavingAccount(validId, validClient, validAmount));
    }
}
