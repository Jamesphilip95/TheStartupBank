package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import org.springframework.http.ResponseEntity;

public interface BankService {
    ResponseEntity<Object> findAllCustomers();

    ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails);

    ResponseEntity<Object> findAccounts(Long customerNumber);

    ResponseEntity<Object> addAccount(AccountDetails accountDetails, Long customerNumber);

    ResponseEntity<Object> depositMoney(Long customerNumber, TransactionDetails transactionDetails);

    ResponseEntity<Object> getCustomer(Long customerNumber);

    ResponseEntity<Object> getAccountBalance(Long customerNumber, String accountType);

    ResponseEntity<Object> withdrawMoney(Long customerNumber, TransactionDetails withdrawDetails);

    ResponseEntity<Object> getTransactions(String accountNumber);

    ResponseEntity<Object> getTransaction(String transactionId);

}

