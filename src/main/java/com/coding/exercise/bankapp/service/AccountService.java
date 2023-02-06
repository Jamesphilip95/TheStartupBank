package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.coding.exercise.bankapp.common.ExceptionHandler.validateAccountResourceFound;
import static com.coding.exercise.bankapp.common.ExceptionHandler.validateCustomer;

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
        validateCustomer(accountDetails.getCustomerNumber(), customerEntityOpt.isPresent());
        Account account = BankServiceHelper.convertAccountToEntity(accountDetails);
        account.setAccountCreatedTime(new Date());
        account.setAccountNumber(UUID.randomUUID());
        accountRepository.save(account);
        return account.getAccountNumber();
    }

    public AccountDetails getAccount(String accountNumber) {
        Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(UUID.fromString(accountNumber));
        validateAccountResourceFound(accountNumber, accountEntityOpt.isPresent());
        return BankServiceHelper.convertToAccountPojo(accountEntityOpt.get());
    }

}
