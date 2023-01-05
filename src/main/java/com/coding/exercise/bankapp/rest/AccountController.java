package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.rest.swagger.AccountControllerDoc;
import com.coding.exercise.bankapp.service.BankService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@Api(tags = { "Accounts REST endpoints" })
public class AccountController implements AccountControllerDoc {

    @Autowired
    BankService bankService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listAccounts(@RequestParam(required = false) Long customerNumber) {
        return bankService.findAccounts(customerNumber);
    }

    @GetMapping(value = "/viewBalance/{customerNumber}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> viewBalance(@PathVariable Long customerNumber, @RequestParam(required = true) String accountNumber) {
        return bankService.getAccountBalance(customerNumber,accountNumber);
    }

    @PostMapping (value = "/{customerNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addAccount(@PathVariable Long customerNumber, @RequestBody @Valid AccountDetails accountDetails) {
        return bankService.addAccount(accountDetails, customerNumber);
    }
}
