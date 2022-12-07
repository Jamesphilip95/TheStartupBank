package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BankService {
    ResponseEntity<Object> findAll();

    ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails);
}
