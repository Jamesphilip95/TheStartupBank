package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.rest.swagger.CustomerControllerDoc;
import com.coding.exercise.bankapp.service.BankService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("customers")
@Api(tags = { "Customer REST endpoints" })
public class CustomerController implements CustomerControllerDoc {

    @Autowired
    BankService bankService;

    @PostMapping (value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody @Valid CustomerDetails customerDetails) {
        return bankService.registerCustomer(customerDetails);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listCustomers() {
        return bankService.findAllCustomers();
    }
}
