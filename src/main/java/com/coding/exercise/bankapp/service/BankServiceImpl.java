package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.coding.exercise.bankapp.TheStartupBankApplication.createID;

@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public ResponseEntity<Object> findAllCustomers() {
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();
        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer ->
            allCustomerDetails.add(bankServiceHelper.convertToCustomerPojo(customer))
        );

        return ResponseEntity.status(HttpStatus.OK).body(allCustomerDetails);
    }

    @Override
    public ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails) {
        Customer customer = bankServiceHelper.convertCustomerToEntity(customerDetails);
        customer.setCustomerNumber(createID());
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer number: " + customer.getCustomerNumber());
    }

    @Override
    public ResponseEntity<Object> findAllAccountsForCustomer(String customerNumber) {
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        Iterable<Account> accountList = accountRepository.findAll();

        accountList.forEach(account -> {
            if(account.getCustomerNumber().equals(Long.valueOf(customerNumber))) {
                allAccountDetails.add(bankServiceHelper.convertToAccountPojo(account));
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(allAccountDetails);
    }

    @Override
    public ResponseEntity<Object> addAccount(AccountDetails accountDetails, String customerNumber) {
        Account account = bankServiceHelper.convertAccountToEntity(accountDetails);
        account.setAccountBalance(0.00);
        account.setAccountCreatedTime(new Date());
        account.setAccountNumber(String.valueOf(UUID.randomUUID()));
        account.setCustomerNumber(Long.valueOf(customerNumber));
        accountRepository.save(account);
        //toDo add logging
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Id: " + account.getAccountNumber());
    }
}
