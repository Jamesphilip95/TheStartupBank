package com.coding.exercise.bankapp.rest.swagger;

import com.coding.exercise.bankapp.pojos.TransferDetails;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface TransferControllerDoc {
    @ApiOperation(value = "Create transfer", notes = "Transfer between accounts")
    UUID createTransfer(
            @RequestBody() @Valid
            TransferDetails transferDetails);

    @ApiOperation(value = "List transfers")
    List<TransferDetails> listTransfers(
            @RequestParam(required = false) String sourceTransactionId,
            @RequestParam(required = false) String targetTransactionId,
            @RequestParam(required = false) String sourceAccountNumber,
            @RequestParam(required = false) String targetAccountNumber);

    @ApiOperation(value = "Get transfer", notes = "Get transfer by transferId")
    TransferDetails getTransfer(
            @PathVariable @Valid @ApiParam
            String transferId);

}
