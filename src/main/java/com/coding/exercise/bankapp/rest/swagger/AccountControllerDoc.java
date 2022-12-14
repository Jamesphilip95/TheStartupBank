package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface AccountControllerDoc {

    @ApiOperation(value = "Add account", notes = "Add new account for customer")
    ResponseEntity<Object> addAccount(String customerNumber, AccountDetails accountDetails);

    @ApiOperation(value = "List accounts", notes = "List accounts for customer")
    ResponseEntity<Object> listAccounts(String customerNumber);

}
