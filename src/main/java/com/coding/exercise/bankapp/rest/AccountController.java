package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.TransferDetails;
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

    @GetMapping(value = "/list/{customerNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> listAccounts(@PathVariable Long customerNumber) {
        return bankService.findAllAccountsForCustomer(customerNumber);
    }

    @PostMapping(value = "/deposit/{customerNumber}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> depositMoney(@PathVariable Long customerNumber, @RequestBody TransferDetails transferDetails) {
        return bankService.depositMoney(customerNumber, transferDetails);
    }

    @PostMapping(value = "/withdraw/{customerNumber}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> withdrawMoney(@PathVariable Long customerNumber, @RequestBody TransferDetails withdrawDetails) {
        return bankService.withdrawMoney(customerNumber, withdrawDetails);
    }

    @GetMapping(value = "/viewBalance/{customerNumber}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> viewBalance(@PathVariable Long customerNumber, @RequestParam(required = true) String accountType) {
        return bankService.getAccountBalance(customerNumber,accountType);
    }

    @PostMapping (value = "/addAccount/{customerNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addAccount(@PathVariable Long customerNumber, @RequestBody @Valid AccountDetails accountDetails) {
        return bankService.addAccount(accountDetails, customerNumber);
    }
}
