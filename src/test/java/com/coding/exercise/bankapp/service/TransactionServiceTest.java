package com.coding.exercise.bankapp.service;


import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.common.BadRequestException;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Transaction;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.TransactionRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @Captor
    ArgumentCaptor<Transaction> transactionArgumentCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;


    @Test
    public void testCreateTransactionHappyPath() {
        Optional<Account> accountEntityOpt = Optional.of(BaseTest.buildAccountEntity());
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        TransactionDetails transactionDetails = BaseTest.buildTransactionDetailsPayload();

        transactionService.createTransaction(transactionDetails);

        verify(transactionRepository).save(transactionArgumentCaptor.capture());
        Transaction actualTransaction = transactionArgumentCaptor.getValue();

        Transaction transaction = BankServiceHelper.convertToTransactionEntity(transactionDetails);

        assertEquals(transaction, actualTransaction);
    }

    @Test
    public void testCreateTransactionAccountNotFound() {
        Optional<Account> accountEntityOpt = Optional.empty();
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        exceptionRule.expect(BadRequestException.class);
        String expectedMessage = "No source account with account number 567e2712-cafe-4204-8449-2059435c24a0";
        exceptionRule.expectMessage(expectedMessage);
        transactionService.createTransaction(BaseTest.buildTransactionDetailsPayload());
    }

    @Test
    public void testGetTransaction() {
        Optional<Transaction> transactionEntityOpt = Optional.of(BaseTest.buildTransactionEntityWithId());
        when(transactionRepository.findById(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(transactionEntityOpt);
        TransactionDetails transactionDetails = BankServiceHelper.convertToTransactionPojo(transactionEntityOpt.get());

        TransactionDetails actualTransactionDetails = transactionService.getTransaction("567e2712-cafe-4204-8449-2059435c24a0");


        assertEquals(transactionDetails.getAccountNumber(),actualTransactionDetails.getAccountNumber());
        assertEquals(transactionDetails.getTransactionID(),actualTransactionDetails.getTransactionID());
        assertEquals(transactionDetails.getTransactionType(), actualTransactionDetails.getTransactionType());
        assertEquals(transactionDetails.getDescription(), actualTransactionDetails.getDescription());
    }

    @Test
    public void testFindAllTransactions() {
        List<Transaction> accountList = new ArrayList<>();
        accountList.add(BaseTest.buildTransactionEntityWithId());
        accountList.add(BaseTest.buildTransactionEntityWithId());
        when(transactionRepository.findAll()).thenReturn(accountList);

        List<TransactionDetails> transactionDetails = transactionService.getTransactions(null);
        assertNotNull(transactionDetails);
        assertEquals(2, transactionDetails.size());
    }

    @Test
    public void testFindAllTransactionsWithAccountNumber() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction1 = BaseTest.buildTransactionEntityWithId();
        transaction1.setAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"));
        Transaction transaction2 = BaseTest.buildTransactionEntityWithId();
        transaction2.setAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"));
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        Optional<List<Transaction>> transactionListEntityOpt = Optional.of(transactionList);

        when(transactionRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(transactionListEntityOpt);

        List<TransactionDetails> accounts = transactionService.getTransactions("567e2712-cafe-4204-8449-2059435c24a0");
        assertNotNull(accounts);
        assertEquals(2, accounts.size());

    }
    @Test
    public void testFindAllTransactionsWhenEmptyRepository() {
        when(transactionRepository.findAll()).thenReturn(new ArrayList<>());

        List<TransactionDetails> transactionDetails = transactionService.getTransactions(null);
        assertNotNull(transactionDetails);
        assertTrue(transactionDetails.isEmpty());
    }
    @Test
    public void testGetTransactionNotFound() {
        Optional<Account> accountEntityOpt = Optional.empty();
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        String expectedMessage = "No transaction with transaction number 567e2712-cafe-4204-8449-2059435c24a0";
        exceptionRule.expectMessage(expectedMessage);
        transactionService.getTransaction("567e2712-cafe-4204-8449-2059435c24a0");
    }

}
