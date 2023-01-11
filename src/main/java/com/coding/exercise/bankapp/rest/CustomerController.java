package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.rest.swagger.CustomerControllerDoc;
import com.coding.exercise.bankapp.service.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("customers")
@Api(tags = { "Customer REST endpoints" })
public class CustomerController implements CustomerControllerDoc {

    @Autowired
    CustomerService customerService;

    @Override
    @PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid CustomerDetails customerDetails) {
        return customerService.registerCustomer(customerDetails);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listCustomers() {
        return customerService.findAllCustomers();
    }

    @Override
    @GetMapping(value = "/{customerNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getCustomer(@PathVariable Long customerNumber) {
        return customerService.getCustomer(customerNumber);
    }
}
