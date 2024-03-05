package com.acme.banking.dbo.service;

import com.acme.banking.dbo.domain.Cash;
import com.acme.banking.dbo.domain.Client;
import com.acme.banking.dbo.domain.SavingAccount;
import com.acme.banking.dbo.exception.AccountNotFoundException;
import com.acme.banking.dbo.repository.AccountRepository;
import com.acme.banking.dbo.repository.ClientRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessingNewTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private Cash cash;

    @InjectMocks
    private Processing sut;

    Client existClient = new Client(1, "Test");


    @Test
    @DisplayName("No account with Id")
    public void shouldNotTransferWhenAccountIdNotFound() {
        final int nonExistAccountId = 1;
        final int accountFromId = 2;
        final int accountToId = 3;
        final double validAmount = 1D;



        when(accountRepository.getById(nonExistAccountId)).thenReturn(Optional.empty());
        when(accountRepository.getById(accountFromId)).thenReturn(Optional.of(new SavingAccount(accountFromId, existClient, validAmount)));
        when(accountRepository.getById(accountToId)).thenReturn(Optional.of(new SavingAccount(accountToId, existClient, validAmount)));


        assertAll("No account with Id",
                () -> assertThrows(AccountNotFoundException.class, () -> sut.transfer(nonExistAccountId, accountToId, validAmount)),
                () -> assertThrows(AccountNotFoundException.class, () -> sut.transfer(accountFromId, nonExistAccountId, validAmount)));
//        assertThrows(AccountNotFoundException.class,
//                () -> sut.transfer(accountFromId, accountToId, validAmount));

//        verify(accountRepository).getById(nonExistAccountId);
//        verify(accountRepository).getById(accountFromId);
//        verify(accountRepository).getById(accountToId);
    }



    @Test
    @DisplayName("Try to transfer to the same account with equal ID")
    public void shouldNotTransferWhenAccountIdIsSame() {
        final int accountFromId = 1;
        final int accountToId = accountFromId;
        final double validAmount = 1D;


        assertThrows(IllegalArgumentException.class,
                () -> sut.transfer(accountFromId, accountToId, validAmount));

    }

    //not enough money

}
