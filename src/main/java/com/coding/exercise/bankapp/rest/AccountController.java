package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.rest.swagger.AccountControllerDoc;
import com.coding.exercise.bankapp.service.AccountService;
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
    AccountService accountService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listAccounts(@RequestParam(required = false) Long customerNumber) {
        return accountService.findAccounts(customerNumber);
    }

    @Override
    @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }


//    @GetMapping(value = "/viewBalance/{accountNumber}",produces = MediaType.APPLICATION_JSON_VALUE)
//    public Double viewBalance(@PathVariable String accountNumber) {
//        return bankService.getAccountBalance(accountNumber);
//    }

    @Override
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountDetails accountDetails) {
        return accountService.createAccount(accountDetails);
    }
}
