package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import org.springframework.http.ResponseEntity;

public interface BankService {
    ResponseEntity<Object> findAllCustomers();

    ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails);

    ResponseEntity<Object> findAllAccountsForCustomer(String customerNumber);

    ResponseEntity<Object> addAccount(AccountDetails accountDetails, String customerNumber);



}
