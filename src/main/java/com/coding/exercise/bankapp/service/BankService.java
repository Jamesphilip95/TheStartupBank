package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.pojos.TransferDetails;
import org.springframework.http.ResponseEntity;

public interface BankService {
    ResponseEntity<Object> findAllCustomers();

    ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails);

    ResponseEntity<Object> findAllAccountsForCustomer(Long customerNumber);

    ResponseEntity<Object> addAccount(AccountDetails accountDetails, Long customerNumber);

    ResponseEntity<Object> depositMoney(Long customerNumber, TransferDetails transferDetails);

    ResponseEntity<Object> getCustomer(Long customerNumber);

    ResponseEntity<Object> getAccountBalance(Long customerNumber, String accountType);

    ResponseEntity<Object> withdrawMoney(Long customerNumber, TransferDetails withdrawDetails);
}

