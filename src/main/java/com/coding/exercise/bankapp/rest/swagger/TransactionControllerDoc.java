package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.TransactionDetails;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface TransactionControllerDoc {

    @ApiOperation(value = "Deposit money", notes = "Deposit money into account")
    ResponseEntity<Object> depositMoney(Long customerNumber, TransactionDetails transactionDetails);

    @ApiOperation(value = "Withdraw money", notes = "Withdraw money into account")
    ResponseEntity<Object> withdrawMoney(Long customerNumber, TransactionDetails transactionDetails);

    @ApiOperation(value = "List", notes = "List transactions")
    ResponseEntity<Object> getTransactions(String accountNumber);

    @ApiOperation(value = "Get transaction", notes = "Get transaction by transactionId")
    ResponseEntity<Object> getTransaction(String transactionId);

}
