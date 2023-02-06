package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.pojos.TransferDetails;
import com.coding.exercise.bankapp.rest.swagger.TransferControllerDoc;
import com.coding.exercise.bankapp.service.TransferService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("transfers")
@Api(tags = { "Transfer REST endpoints" })
public class TransferController implements TransferControllerDoc {

    @Autowired
    TransferService transferService;

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public UUID createTransfer(@RequestBody TransferDetails transferDetails) {
        return transferService.createTransfer(transferDetails);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransferDetails> listTransfers(
            @RequestParam(required = false) String sourceTransactionId,
            @RequestParam(required = false) String targetTransactionId,
            @RequestParam(required = false) String sourceAccountNumber,
            @RequestParam(required = false) String targetAccountNumber) {
        return transferService.getTransfers(sourceTransactionId,
                targetTransactionId,
                sourceAccountNumber,
                targetAccountNumber);
    }

    @Override
    @GetMapping(value = "/{transferId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TransferDetails getTransfer(@PathVariable String transferId) {
        return transferService.getTransfer(transferId);
    }
}
