//package com.acme.banking.dbo.service;
//
//import com.acme.banking.dbo.domain.Account;
//import com.acme.banking.dbo.domain.Cash;
//import com.acme.banking.dbo.domain.Client;
//import com.acme.banking.dbo.domain.SavingAccount;
//import com.acme.banking.dbo.exception.AccountNotFoundException;
//import com.acme.banking.dbo.exception.ClientNotFoundException;
//import com.acme.banking.dbo.exception.TransferAccountAmountException;
//import com.acme.banking.dbo.repository.AccountRepository;
//import com.acme.banking.dbo.repository.ClientRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProcessingTest {
//
//    @Mock
//    private AccountRepository accountRepository;
//
//    @Mock
//    private ClientRepository clientRepository;
//
//    @Mock
//    private Cash cash;
//
//    @InjectMocks
//    private Processing processing;
//
//    private final int CLIENT_ID = 1;
//
//    @Test
//    void createClient_success() {
//        String clientName = "Test Name";
//        Client expectedClient = new Client(CLIENT_ID, clientName);
//
//        when(clientRepository.generateId()).thenReturn(CLIENT_ID);
//
//        Client actualClient = processing.createClient(clientName);
//
//        assertEquals(expectedClient, actualClient);
//
//        verify(clientRepository).generateId();
//        verify(clientRepository).save(expectedClient);
//    }
//
//    @Test
//    void createClient_unsuccessfulInvalidName() {
//        String emptyClientName = "";
//
//        when(clientRepository.generateId()).thenReturn(CLIENT_ID);
//
//        assertThrows(IllegalArgumentException.class,
//                () -> processing.createClient(emptyClientName));
//
//        verify(clientRepository).generateId();
//        verifyNoMoreInteractions(clientRepository);
//    }
//
//    @Test
//    void getAccountsByClientId_success() {
//        Client stub = mock(Client.class);
//
//        int accountId = 123;
//        double amount = 1000.0;
//        SavingAccount expectedAccount = new SavingAccount(accountId, stub, amount);
//
//        when(clientRepository.getById(CLIENT_ID)).thenReturn(Optional.of(stub));
//        when(stub.getAccounts()).thenReturn(List.of(expectedAccount));
//
//        Collection<Account> actualAccounts = processing.getAccountsByClientId(CLIENT_ID);
//
//        assertEquals(List.of(expectedAccount), actualAccounts);
//
//        verify(clientRepository).getById(CLIENT_ID);
//    }
//
//    @Test
//    void getAccountsByClientId_unsuccessfulClientNotPresented() {
//        when(clientRepository.getById(CLIENT_ID)).thenReturn(Optional.empty());
//
//        assertThrows(ClientNotFoundException.class,
//                () -> processing.getAccountsByClientId(CLIENT_ID));
//
//        verify(clientRepository).getById(CLIENT_ID);
//    }
//
//    @Test
//    void transfer_success() {
//        Client stub = mock(Client.class);
//
//        int accountFromId = 123;
//        double amountFrom = 3000.0;
//        SavingAccount accountFrom = new SavingAccount(accountFromId, stub, amountFrom);
//
//        int accountToId = 124;
//        double amountTo = 1000.0;
//        SavingAccount accountTo = new SavingAccount(accountToId, stub, amountTo);
//
//        double deltaAmount = 500.0;
//
//        SavingAccount accountFromAfterTransfer = new SavingAccount(accountFromId, stub, amountFrom - deltaAmount);
//        SavingAccount accountToAfterTransfer = new SavingAccount(accountToId, stub, amountTo + deltaAmount);
//
//        when(accountRepository.getById(accountFromId)).thenReturn(Optional.of(accountFrom));
//        when(accountRepository.getById(accountToId)).thenReturn(Optional.of(accountTo));
//
//        processing.transfer(accountFromId, accountToId, deltaAmount);
//
//        verify(accountRepository).getById(accountFromId);
//        verify(accountRepository).getById(accountToId);
//        verify(accountRepository).save(accountFromAfterTransfer);
//        verify(accountRepository).save(accountToAfterTransfer);
//    }
//
//    @Test
//    void transfer_accountFromNotPresented() {
//        int accountFromId = 123;
//        int accountToId = 123;
//        double deltaAmount = 500.0;
//
//        when(accountRepository.getById(accountFromId)).thenReturn(Optional.empty());
//
//        assertThrows(AccountNotFoundException.class,
//                () -> processing.transfer(accountFromId, accountToId, deltaAmount));
//
//        verify(accountRepository).getById(accountFromId);
//        verifyNoMoreInteractions(accountRepository);
//    }
//
//    @Test
//    void transfer_accountToNotPresented() {
//        Client stub = mock(Client.class);
//
//        int accountFromId = 123;
//        double amountFrom = 3000.0;
//        SavingAccount accountFrom = new SavingAccount(accountFromId, stub, amountFrom);
//
//        int accountToId = 124;
//
//        double deltaAmount = 500.0;
//
//        when(accountRepository.getById(accountFromId)).thenReturn(Optional.of(accountFrom));
//        when(accountRepository.getById(accountToId)).thenReturn(Optional.empty());
//
//        assertThrows(AccountNotFoundException.class,
//                () -> processing.transfer(accountFromId, accountToId, deltaAmount));
//
//        verify(accountRepository).getById(accountFromId);
//        verify(accountRepository).getById(accountToId);
//        verifyNoMoreInteractions(accountRepository);
//    }
//
//    @Test
//    void transfer_accountFromAmountLessThanTransferredAmount() {
//        Client stub = mock(Client.class);
//
//        int accountFromId = 123;
//        double amountFrom = 300.0;
//        SavingAccount accountFrom = new SavingAccount(accountFromId, stub, amountFrom);
//
//        int accountToId = 124;
//        double amountTo = 1000.0;
//        SavingAccount accountTo = new SavingAccount(accountToId, stub, amountTo);
//
//        double deltaAmount = 500.0;
//
//        when(accountRepository.getById(accountFromId)).thenReturn(Optional.of(accountFrom));
//        when(accountRepository.getById(accountToId)).thenReturn(Optional.of(accountTo));
//
//        assertThrows(TransferAccountAmountException.class,
//                () -> processing.transfer(accountFromId, accountToId, deltaAmount));
//
//        verify(accountRepository).getById(accountFromId);
//        verify(accountRepository).getById(accountToId);
//        verifyNoMoreInteractions(accountRepository);
//    }
//
//    @Test
//    void cash_success() {
//        int fromAccountId = 123;
//        double amount = 300.0;
//
//        processing.cash(amount, fromAccountId);
//
//        verify(cash).log(amount, fromAccountId);
//    }
//}