package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    @Autowired
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<Object> findAccounts(Long customerNumber) {
        if(customerNumber!= null) {
            return findAccountsByCustomerNumber(customerNumber);
        }
        Iterable<Account> accountList = accountRepository.findAll();
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        accountList.forEach(account ->
                allAccountDetails.add(bankServiceHelper.convertToAccountPojo(account))
        );
        return ResponseEntity.status(HttpStatus.OK).body(allAccountDetails);
    }

    private ResponseEntity<Object> findAccountsByCustomerNumber(Long customerNumber) {
        Optional<List<Account>> accountListEntityOpt = accountRepository.findByCustomerNumber(customerNumber);
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        accountListEntityOpt.ifPresent(accounts -> accounts.forEach(account ->
                allAccountDetails.add(bankServiceHelper.convertToAccountPojo(account))
        ));
        return ResponseEntity.status(HttpStatus.OK).body(allAccountDetails);
    }
    public ResponseEntity<Object> createAccount(AccountDetails accountDetails) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(accountDetails.getCustomerNumber());
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No customer with customerNumber: " + accountDetails.getCustomerNumber());
        }
        Account account = bankServiceHelper.convertAccountToEntity(accountDetails);
        account.setAccountCreatedTime(new Date());
        account.setAccountNumber(UUID.randomUUID());
        account.setCustomerNumber(account.getCustomerNumber());
        accountRepository.save(account);
        //toDo add logging
        return ResponseEntity.status(HttpStatus.CREATED).body(account.getAccountNumber());
    }

    public ResponseEntity<Object> getAccount(String accountNumber) {
        Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(UUID.fromString(accountNumber));
        return accountEntityOpt
                .<ResponseEntity<Object>>map(
                        account -> ResponseEntity.status(HttpStatus.OK).body(bankServiceHelper.convertToAccountPojo(account)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
