package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Transfer;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.pojos.TransferDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.TransferRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.coding.exercise.bankapp.common.ExceptionHandler.validateSourceAccount;
import static com.coding.exercise.bankapp.common.ExceptionHandler.validateTargetAccount;

@Service
public class TransferService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransferRepository transferRepository;

    public UUID createTransfer(TransferDetails transferDetails) {
        Optional<Account> sourceAccountEntityOpt = accountRepository.findByAccountNumber(UUID.fromString(transferDetails.getSourceAccount()));
        Optional<Account> targetAccountEntityOpt = accountRepository.findByAccountNumber(UUID.fromString(transferDetails.getTargetAccount()));

        validateSourceAccount(transferDetails.getSourceAccount(), sourceAccountEntityOpt.isPresent());
        validateTargetAccount(transferDetails.getTargetAccount(), targetAccountEntityOpt.isPresent());
        TransactionDetails sourceTransactionDetails = BankServiceHelper.buildSourceTransaction(transferDetails);
        TransactionDetails targetTransactionDetails = BankServiceHelper.buildTargetTransaction(transferDetails);
        UUID sourceTransactionId = transactionService.createTransaction(sourceTransactionDetails);
        UUID targetTransactionId = transactionService.createTransaction(targetTransactionDetails);
        Transfer transfer = BankServiceHelper.convertToTransferEntity(sourceTransactionId, targetTransactionId);
        transferRepository.save(transfer);
        return transfer.getId();
    }


    public List<TransferDetails> getTransfers(String sourceTransactionId, String targetTransactionId, String sourceAccountNumber, String targetAccountNumber) {
//        if(sourceTransactionId!=null) {
//            return getTransactionsByAccountNumber(accountNumber); //TODO ask about the best way to filter with query parameters
//        }
        List<TransferDetails> transferDetailsList = new ArrayList<>();
        Iterable<Transfer> transferList = transferRepository.findAll();
        transferList.forEach(transfer ->
                transferDetailsList.add(BankServiceHelper.convertToTransferPojo(transfer)
        ));
        return transferDetailsList;
    }

    public TransferDetails getTransfer(String transferId) {
        Optional<Transfer> transactionEntityOpt = transferRepository.findById(UUID.fromString(transferId));
        if(!transactionEntityOpt.isPresent()) {
            throw new ResourceNotFoundException("No transfer with transfer id " + transferId);
        }
        return BankServiceHelper.convertToTransferPojo(transactionEntityOpt.get());
    }
}
