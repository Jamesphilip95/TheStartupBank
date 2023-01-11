package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.rest.swagger.TransactionControllerDoc;
import com.coding.exercise.bankapp.service.TransactionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
@Api(tags = { "Transaction REST endpoints" })
public class TransactionController implements TransactionControllerDoc {

    @Autowired
    TransactionService bankService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionDetails transactionDetails) {
        return bankService.createTransaction(transactionDetails);
    }

//    @PostMapping(value = "/withdraw/{customerNumber}",consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> withdrawMoney(@PathVariable Long customerNumber, @RequestBody TransactionDetails withdrawDetails) {
//        return bankService.withdrawMoney(customerNumber, withdrawDetails);
//    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listTransactions(@RequestParam (required = false) String accountNumber) {
        return bankService.getTransactions(accountNumber);
    }

    @Override
    @GetMapping(value = "/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getTransaction(@PathVariable String transactionId) {
        return bankService.getTransaction(transactionId);
    }
}
