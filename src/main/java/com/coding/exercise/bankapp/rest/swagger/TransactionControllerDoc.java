package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.TransactionDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface TransactionControllerDoc {
    @ApiOperation(value = "Create transaction", notes = "Deposit, withdraw or transfer funds")
    UUID createTransaction(
            @RequestBody() @Valid @ApiParam(value = "TransferType is the either TRANSFER or CASH")
            TransactionDetails transactionDetails);

    @ApiOperation(value = "List", notes = "List transactions")
    List<TransactionDetails> listTransactions(String accountNumber);

    @ApiOperation(value = "Get transaction", notes = "Get transaction by transactionId")
    TransactionDetails getTransaction(
            @PathVariable @Valid @ApiParam
            String transactionId);

}
