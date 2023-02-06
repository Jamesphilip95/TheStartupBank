package com.coding.exercise.bankapp.service;


import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.common.BadRequestException;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
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

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @Captor
    ArgumentCaptor<Account> accountCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private AccountService accountService;


    @Test
    public void testCreateAccountHappyPath() {
        Optional<Customer> customerEntityOpt = Optional.of(BaseTest.buildCustomerEntity());
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        AccountDetails accountDetails = BaseTest.buildAccountDetailsPayload();

        accountService.createAccount(accountDetails);

        verify(accountRepository).save(accountCaptor.capture());
        Account actualAccount = accountCaptor.getValue();

        Account account = BankServiceHelper.convertAccountToEntity(accountDetails);
        account.setAccountCreatedTime(actualAccount.getAccountCreatedTime());
        account.setAccountNumber(actualAccount.getAccountNumber());
        account.setCustomerNumber(account.getCustomerNumber());

        assertEquals(account, actualAccount);
    }

    @Test
    public void testCreateAccountNotFound() {
        Optional<Customer> customerEntityOpt = Optional.empty();
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        exceptionRule.expect(BadRequestException.class);
        String expectedMessage = "No customer with customer number 12345";
        exceptionRule.expectMessage(expectedMessage);
        accountService.createAccount(BaseTest.buildAccountDetailsPayload());
    }

    @Test
    public void testGetAccount() {
        Optional<Account> accountEntityOpt = Optional.of(BaseTest.buildAccountEntity());
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        AccountDetails accountDetails = BankServiceHelper.convertToAccountPojo(accountEntityOpt.get());

        AccountDetails actualAccountDetails = accountService.getAccount("567e2712-cafe-4204-8449-2059435c24a0");


        assertEquals(accountDetails.getCustomerNumber(), actualAccountDetails.getCustomerNumber());
        assertEquals(accountDetails.getAccountNumber(), actualAccountDetails.getAccountNumber());
        assertEquals(accountDetails.getAccountCreatedTime(), actualAccountDetails.getAccountCreatedTime());
    }

    @Test
    public void testFindAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(BaseTest.buildAccountEntity());
        accountList.add(BaseTest.buildAccountEntity());
        when(accountRepository.findAll()).thenReturn(accountList);

        List<AccountDetails> accountDetails = accountService.findAccounts(null);
        assertNotNull(accountDetails);
        assertEquals(2, accountDetails.size());
    }

    @Test
    public void testFindAllAccountsWithCustomerNumber() {
        List<Account> accountList = new ArrayList<>();
        Account account1 = BaseTest.buildAccountEntity();
        account1.setCustomerNumber(12345L);
        Account account2 = BaseTest.buildAccountEntity();
        account2.setCustomerNumber(12345L);
        accountList.add(account1);
        accountList.add(account2);
        Optional<List<Account>> accountListEntityOpt = Optional.of(accountList);

        when(accountRepository.findByCustomerNumber(12345L)).thenReturn(accountListEntityOpt);
        List<AccountDetails> accounts = accountService.findAccounts(12345L);
        assertNotNull(accounts);
        assertEquals(2, accounts.size());

    }
    @Test
    public void testFindAllAccountsWhenEmptyRepository() {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        List<AccountDetails> accountDetails = accountService.findAccounts(null);
        assertNotNull(accountDetails);
        assertTrue(accountDetails.isEmpty());
    }
    @Test
    public void testGetAccountNotFound() {
        Optional<Account> accountEntityOpt = Optional.empty();
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        String expectedMessage = "No account with account number 567e2712-cafe-4204-8449-2059435c24a0";
        exceptionRule.expectMessage(expectedMessage);
        accountService.getAccount("567e2712-cafe-4204-8449-2059435c24a0");
    }

}
