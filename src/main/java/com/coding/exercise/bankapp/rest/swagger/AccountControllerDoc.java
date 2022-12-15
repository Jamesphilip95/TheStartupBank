package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.TransferDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface AccountControllerDoc {

    @ApiOperation(value = "Add account", notes = "Add new account for customer")
    ResponseEntity<Object> addAccount(Long customerNumber, AccountDetails accountDetails);

    @ApiOperation(value = "List accounts", notes = "List accounts for customer")
    ResponseEntity<Object> listAccounts(Long customerNumber);

    @ApiOperation(value = "Deposit money", notes = "Deposit money into account")
    ResponseEntity<Object> depositMoney(Long customerNumber, TransferDetails transferDetails);

    @ApiOperation(value = "Withdraw money", notes = "Withdraw money into account")
    ResponseEntity<Object> withdrawMoney(Long customerNumber, TransferDetails transferDetails);

    @ApiOperation(value = "View balance", notes = "View balance")
    ResponseEntity<Object> viewBalance(Long customerNumber, String accountType);

}
