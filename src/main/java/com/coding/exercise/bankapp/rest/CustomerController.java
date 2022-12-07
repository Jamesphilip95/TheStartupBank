package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    BankService bankService;

    @PostMapping(path = "registerCustomer")
    public ResponseEntity<Object> registerCustomer(@RequestBody CustomerDetails customerDetails) {
        return bankService.registerCustomer(customerDetails);
    }
}
