package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface AccountControllerDoc {

    @ApiOperation(value = "Add account", notes = "Add new account for customer")
    ResponseEntity<Object> addAccount(Long customerNumber, AccountDetails accountDetails);

    @ApiOperation(value = "List accounts", notes = "List accounts")
    ResponseEntity<Object> listAccounts(Long customerNumber);

    @ApiOperation(value = "View balance", notes = "View balance")
    ResponseEntity<Object> viewBalance(Long customerNumber, String accountNumber);

}
