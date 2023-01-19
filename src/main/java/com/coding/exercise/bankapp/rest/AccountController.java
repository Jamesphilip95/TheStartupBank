package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.rest.swagger.AccountControllerDoc;
import com.coding.exercise.bankapp.service.AccountService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
@Api(tags = { "Accounts REST endpoints" })
public class AccountController implements AccountControllerDoc {

    @Autowired
    AccountService accountService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AccountDetails> listAccounts(@RequestParam(required = false) Long customerNumber) {
        return accountService.findAccounts(customerNumber);
    }

    @Override
    @GetMapping(value = "/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDetails getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @Override
    @PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountDetails accountDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountDetails));
    }
}
