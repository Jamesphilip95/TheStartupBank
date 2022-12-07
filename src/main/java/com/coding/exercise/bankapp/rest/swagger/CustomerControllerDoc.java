package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface CustomerControllerDoc {

    @ApiOperation(value = "Register", notes = "Registration for new customers")
    ResponseEntity<Object> register(CustomerDetails customerDetails);

    @ApiOperation(value = "List", notes = "List all customers")
    ResponseEntity<Object> listCustomers();

}
