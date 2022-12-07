package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService{

    @Override
    public ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails) {
        return null;
    }
}
