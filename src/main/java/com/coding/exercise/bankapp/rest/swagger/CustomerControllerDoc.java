package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.CustomerDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;


public interface CustomerControllerDoc {

    @ApiOperation(value = "Register", notes = "Registration for new customers")
    Long createCustomer(
            @RequestBody @Valid @ApiParam CustomerDetails customerDetails);

    @ApiOperation(value = "List customers", notes = "List all customers")
    List<CustomerDetails> listCustomers();

    @ApiOperation(value = "Get Customer", notes = "Get customer with customerNumber")
    CustomerDetails getCustomer(Long customerNumber);


}
