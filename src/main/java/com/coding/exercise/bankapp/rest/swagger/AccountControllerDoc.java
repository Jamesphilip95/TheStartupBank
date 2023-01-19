package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.AccountDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface AccountControllerDoc {

    @ApiOperation(value = "Add account", notes = "Add new account for customer")
    ResponseEntity<Object> createAccount(
            @RequestBody @Valid @ApiParam AccountDetails accountDetails );

    @ApiOperation(value = "List accounts", notes = "List accounts")
    List<AccountDetails> listAccounts(Long customerNumber);

    @ApiOperation(value = "Get account", notes = "Get account")
    AccountDetails getAccount(
            @PathVariable String accountNumber);

}
