package com.coding.exercise.bankapp.service;


import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.common.BadRequestException;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Transfer;
import com.coding.exercise.bankapp.pojos.TransferDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.TransferRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransferServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @Captor
    ArgumentCaptor<Transfer> transferArgumentCaptor;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private TransferRepository transferRepository;
    @Autowired
    private TransferService transferService;


    @Test
    public void testCreateTransferHappyPath() {
        Optional<Account> accountEntityOpt1 = Optional.of(BaseTest.buildAccountEntity());
        Optional<Account> accountEntityOpt2 = Optional.of(BaseTest.buildAccount2Entity());
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt1);
        when(accountRepository.findByAccountNumber(UUID.fromString("127e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt2);
        when(transactionService.createTransaction(any())).thenReturn(UUID.randomUUID());
        TransferDetails transferDetails = BaseTest.buildTransferDetailsPayload();

        transferService.createTransfer(transferDetails);

        verify(transferRepository).save(transferArgumentCaptor.capture());
        Transfer actualTransfer = transferArgumentCaptor.getValue();

        Transfer transfer = BankServiceHelper.convertToTransferEntity(actualTransfer.getSourceTransactionID(),actualTransfer.getTargetTransactionID());

        assertEquals(transfer, actualTransfer);
    }

    @Test
    public void testCreateSourceAccountNotFound() {
        Optional<Account> accountEntityOpt = Optional.empty();
        TransferDetails transferDetails = BaseTest.buildTransferDetailsPayload();
        when(accountRepository.findByAccountNumber(UUID.fromString(transferDetails.getSourceAccount()))).thenReturn(accountEntityOpt);
        exceptionRule.expect(BadRequestException.class);
        String expectedMessage = "No source account with account number 567e2712-cafe-4204-8449-2059435c24a0";
        exceptionRule.expectMessage(expectedMessage);
        transferService.createTransfer(BaseTest.buildTransferDetailsPayload());
    }

    @Test
    public void testCreateTargetAccountNotFound() {
        Optional<Account> accountEntityOpt2 = Optional.empty();
        Optional<Account> accountEntityOpt1 = Optional.of(BaseTest.buildAccountEntity());
        TransferDetails transferDetails = BaseTest.buildTransferDetailsPayload();
        when(accountRepository.findByAccountNumber(UUID.fromString(transferDetails.getSourceAccount()))).thenReturn(accountEntityOpt1);
        when(accountRepository.findByAccountNumber(UUID.fromString(transferDetails.getTargetAccount()))).thenReturn(accountEntityOpt2);
        exceptionRule.expect(BadRequestException.class);
        String expectedMessage = "No target account with account number 127e2712-cafe-4204-8449-2059435c24a0";
        exceptionRule.expectMessage(expectedMessage);
        transferService.createTransfer(transferDetails);
    }

    @Test
    public void testGetTransfer() {
        Optional<Transfer> transferEntityOpt = Optional.of(BaseTest.buildTransferEntity());
        when(transferRepository.findById(UUID.fromString("147e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(transferEntityOpt);
        TransferDetails transferDetails = BankServiceHelper.convertToTransferPojo(transferEntityOpt.get());

        TransferDetails actualTransferDetails = transferService.getTransfer("147e2712-cafe-4204-8449-2059435c24a0");


        assertEquals(transferDetails.getTransferId(),actualTransferDetails.getTransferId());
        assertEquals(transferDetails.getSourceTransactionID(),actualTransferDetails.getSourceTransactionID());
        assertEquals(transferDetails.getTargetTransactionID(), actualTransferDetails.getTargetTransactionID());
    }
//
//    @Test
//    public void testFindAllCustomers() {
//        List<Transaction> accountList = new ArrayList<>();
//        accountList.add(BaseTest.buildTransactionEntityWithId());
//        accountList.add(BaseTest.buildTransactionEntityWithId());
//        when(transactionRepository.findAll()).thenReturn(accountList);
//
//        List<TransactionDetails> transactionDetails = transactionService.getTransactions(null);
//        assertNotNull(transactionDetails);
//        assertEquals(2, transactionDetails.size());
//    }
//

//    @Test
//    public void testFindAllTransactionsWhenEmptyRepository() {
//        when(transactionRepository.findAll()).thenReturn(new ArrayList<>());
//
//        List<TransactionDetails> transactionDetails = transactionService.getTransactions(null);
//        assertNotNull(transactionDetails);
//        assertTrue(transactionDetails.isEmpty());
//    }
    @Test
    public void testGetTransferNotFound() {
        Optional<Transfer> accountEntityOpt = Optional.empty();
        when(transferRepository.findById(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        String expectedMessage = "No transfer with transfer id 567e2712-cafe-4204-8449-2059435c24a0";
        exceptionRule.expectMessage(expectedMessage);
        transferService.getTransfer("567e2712-cafe-4204-8449-2059435c24a0");
    }

}
