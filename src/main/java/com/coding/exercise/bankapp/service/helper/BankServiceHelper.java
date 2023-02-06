package com.coding.exercise.bankapp.service.helper;

import com.coding.exercise.bankapp.model.*;
import com.coding.exercise.bankapp.pojos.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BankServiceHelper {

    private static long customerNumber = 1;

    public static synchronized Long createCustomerNumber()
    {
        return customerNumber++;
    }


    public static Customer convertCustomerToEntity(CustomerDetails customerDetails) {
        return Customer.builder()
                .firstName(customerDetails.getFirstName())
                .lastName(customerDetails.getLastName())
                .customerNumber(customerDetails.getCustomerNumber())
                .createDateTime(customerDetails.getCreateDateTime())
                .email(customerDetails.getEmail())
                .mobile(customerDetails.getMobileNumber())
                .build();
    }


    public static CustomerDetails convertToCustomerPojo(Customer customer) {
        return CustomerDetails.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .createDateTime(customer.getCreateDateTime())
                .customerNumber(customer.getCustomerNumber())
                .email(customer.getEmail())
                .mobileNumber(customer.getMobile())
                .build();
    }

    public static AccountDetails convertToAccountPojo(Account account) {
        return AccountDetails.builder()
                .accountNumber(account.getAccountNumber())
                .customerNumber(account.getCustomerNumber())
                .accountCreatedTime(account.getAccountCreatedTime())
                .build();
    }
    public static Account convertAccountToEntity(AccountDetails accountDetails) {
        return Account.builder()
                .customerNumber(accountDetails.getCustomerNumber())
                .accountNumber(accountDetails.getAccountNumber())
                .accountCreatedTime(accountDetails.getAccountCreatedTime())
                .build();
    }
    public static Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {
        return Transaction.builder()
                .amount(transactionDetails.getAmount())
                .accountNumber(UUID.fromString(transactionDetails.getAccountNumber()))
                .description(transactionDetails.getDescription())
                .transactionType(transactionDetails.getTransactionType())
                .build();
    }

    public static TransactionDetails convertToTransactionPojo(Transaction transaction) {
        return TransactionDetails.builder()
                .accountNumber(transaction.getAccountNumber().toString())
                .amount(transaction.getAmount())
                .transactionID(transaction.getId().toString())
                .transactionType(transaction.getTransactionType())
                .description(transaction.getDescription())
                .build();
    }

    public static Transfer convertToTransferEntity(UUID sourceTransactionId, UUID targetTransactionId) {
        return Transfer.builder()
                .sourceTransactionID(sourceTransactionId)
                .targetTransactionID(targetTransactionId)
                .build();
    }

    public static TransactionDetails buildTargetTransaction(TransferDetails transferDetails) {
        return TransactionDetails.builder()
                .amount(transferDetails.getAmount())
                .description(transferDetails.getDescription())
                .accountNumber(transferDetails.getTargetAccount())
                .transactionType(TransactionType.TRANSFER)
                .build();
    }

    public static TransactionDetails buildSourceTransaction(TransferDetails transferDetails) {
        return TransactionDetails.builder()
                .amount(transferDetails.getAmount().negate())
                .description(transferDetails.getDescription())
                .accountNumber(transferDetails.getSourceAccount())
                .transactionType(TransactionType.TRANSFER)
                .build();
    }

    public static TransferDetails convertToTransferPojo(Transfer transfer) {
        return TransferDetails.builder()
                .sourceTransactionID(transfer.getSourceTransactionID())
                .targetTransactionID(transfer.getTargetTransactionID())
                .transferId(transfer.getId())
                .build();
    }
}
