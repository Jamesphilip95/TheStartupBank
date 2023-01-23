package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.common.BadRequestException;
import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<AccountDetails> findAccounts(Long customerNumber) {
        if(customerNumber!= null) {
            return findAccountsByCustomerNumber(customerNumber);
        }
        Iterable<Account> accountList = accountRepository.findAll();
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        accountList.forEach(account ->
                allAccountDetails.add(BankServiceHelper.convertToAccountPojo(account))
        );
        return allAccountDetails;
    }

    private List<AccountDetails> findAccountsByCustomerNumber(Long customerNumber) {
        Optional<List<Account>> accountListEntityOpt = accountRepository.findByCustomerNumber(customerNumber);
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        accountListEntityOpt.ifPresent(accounts -> accounts.forEach(account ->
                allAccountDetails.add(BankServiceHelper.convertToAccountPojo(account))
        ));
        return allAccountDetails;
    }
    public UUID createAccount(AccountDetails accountDetails) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(accountDetails.getCustomerNumber());
        if(!customerEntityOpt.isPresent()) {
            throw new BadRequestException("No customer with customerNumber: " + accountDetails.getCustomerNumber());
        }
        Account account = BankServiceHelper.convertAccountToEntity(accountDetails);
        account.setAccountCreatedTime(new Date());
        account.setAccountNumber(UUID.randomUUID());
        account.setCustomerNumber(account.getCustomerNumber());
        accountRepository.save(account);
        return account.getAccountNumber();
    }

    public AccountDetails getAccount(String accountNumber) {
        Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(UUID.fromString(accountNumber));
        if(!accountEntityOpt.isPresent()){
            throw new ResourceNotFoundException("No account with accountNumber: " + accountNumber);
        }
        return BankServiceHelper.convertToAccountPojo(accountEntityOpt.get());
    }

}
