package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.rest.swagger.TransactionControllerDoc;
import com.coding.exercise.bankapp.service.TransactionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
@Api(tags = { "Transaction REST endpoints" })
public class TransactionController implements TransactionControllerDoc {

    @Autowired
    TransactionService bankService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UUID> createTransaction(@RequestBody TransactionDetails transactionDetails) {
        UUID transactionId = bankService.createTransaction(transactionDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionId);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDetails> listTransactions(@RequestParam (required = false) String accountNumber) {
        return bankService.getTransactions(accountNumber);
    }

    @Override
    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDetails getTransaction(@PathVariable String transactionId) {
        return bankService.getTransaction(transactionId);
    }
}
