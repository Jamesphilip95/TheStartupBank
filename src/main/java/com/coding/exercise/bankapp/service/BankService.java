package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import org.springframework.http.ResponseEntity;

public interface BankService {
    ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails);
}
