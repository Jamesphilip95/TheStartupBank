package com.coding.exercise.bankapp.service;


import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private AccountService accountService;

    @Test
    public void testCreateAccountHappyPath() {
        Optional<Customer> customerEntityOpt = Optional.of(BaseTest.buildCustomerEntity());
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        AccountDetails accountDetails = BaseTest.buildAccountDetailsPayload();
        Account account = BaseTest.buildAccountEntity();
        when(bankServiceHelper.convertAccountToEntity(accountDetails)).thenReturn(account);
        Date date = new Date();

        ResponseEntity<Object> objectResponseEntity = accountService.createAccount(accountDetails);

        assertEquals(date.getDay(), account.getAccountCreatedTime().getDay()); //toDo do this better
        assertEquals(date.getMonth(), account.getAccountCreatedTime().getMonth());
        assertEquals(date.getYear(), account.getAccountCreatedTime().getYear());

        assertEquals(Long.valueOf(12345), account.getCustomerNumber());

        assertNotNull(account.getAccountNumber());

        verify(accountRepository).save(account);

        assertEquals(201, objectResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testCreateAccountNotFound() {
        Optional<Customer> customerEntityOpt = Optional.empty();
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);

        ResponseEntity<Object> objectResponseEntity = accountService.createAccount(BaseTest.buildAccountDetailsPayload());


        assertEquals(400, objectResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetAccount() {
        Optional<Account> accountEntityOpt = Optional.of(BaseTest.buildAccountEntity());
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        when(bankServiceHelper.convertToAccountPojo(any())).thenReturn(BaseTest.buildAccountDetailsPayload());

        ResponseEntity<Object> accountResponseEntity = accountService.getAccount("567e2712-cafe-4204-8449-2059435c24a0");
        assertTrue(accountResponseEntity.getBody() instanceof AccountDetails);
        assertEquals(200, accountResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testFindAllCustomers() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(BaseTest.buildAccountEntity());
        accountList.add(BaseTest.buildAccountEntity());
        when(accountRepository.findAll()).thenReturn(accountList);
        when(bankServiceHelper.convertToAccountPojo(any())).thenReturn(BaseTest.buildAccountDetailsPayload());

        ResponseEntity<Object> accountsResponseEntity = accountService.findAccounts(null);
        assertTrue(accountsResponseEntity.getBody() instanceof List<?>);
        List<AccountDetails> body = (List<AccountDetails>) accountsResponseEntity.getBody();
        assertEquals(2, body.size() );
        assertEquals(200, accountsResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testFindAllCustomersWithCustomerNumber() {
        List<Account> accountList = new ArrayList<>();
        Account account1 = BaseTest.buildAccountEntity();
        account1.setCustomerNumber(12345L);
        Account account2 = BaseTest.buildAccountEntity();
        account2.setCustomerNumber(12345L);
        accountList.add(account1);
        accountList.add(account2);
        Optional<List<Account>> accountListEntityOpt = Optional.of(accountList);

        when(accountRepository.findByCustomerNumber(12345L)).thenReturn(accountListEntityOpt);
        when(bankServiceHelper.convertToAccountPojo(any())).thenReturn(BaseTest.buildAccountDetailsPayload());

        ResponseEntity<Object> accountsResponseEntity = accountService.findAccounts(12345L);
        assertTrue(accountsResponseEntity.getBody() instanceof List<?>);
        List<AccountDetails> body = (List<AccountDetails>) accountsResponseEntity.getBody();
        assertEquals(2, body.size() );
        assertEquals(200, accountsResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testFindAllCustomersWhenEmptyRepository() {
        List<Account> accountList = new ArrayList<>();
        when(accountRepository.findAll()).thenReturn(accountList);
        when(bankServiceHelper.convertToAccountPojo(any())).thenReturn(BaseTest.buildAccountDetailsPayload());

        ResponseEntity<Object> accountsResponseEntity = accountService.findAccounts(null);
        assertTrue(accountsResponseEntity.getBody() instanceof List<?>);
        List<AccountDetails> body = (List<AccountDetails>) accountsResponseEntity.getBody();
        assertEquals(0, body.size() );
        assertEquals(200, accountsResponseEntity.getStatusCodeValue());
    }
    @Test
    public void testGetAccountNotFound() {
        Optional<Account> accountEntityOpt = Optional.empty();
        when(accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))).thenReturn(accountEntityOpt);
        when(bankServiceHelper.convertToAccountPojo(any())).thenReturn(BaseTest.buildAccountDetailsPayload());

        ResponseEntity<Object> accountResponseEntity = accountService.getAccount("567e2712-cafe-4204-8449-2059435c24a0");
        assertEquals(404, accountResponseEntity.getStatusCodeValue());
    }

}
