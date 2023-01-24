package com.coding.exercise.bankapp.service.helper;

import com.coding.exercise.bankapp.model.*;
import com.coding.exercise.bankapp.pojos.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BankServiceHelper { //toDo change to static methods

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

//    private Transaction getSecondaryTransaction(TransactionDetails transactionDetails) {
//        return Transaction.builder()
//                .amount(transactionDetails.getAmount() * -1)
//                .accountNumber(UUID.fromString(transactionDetails.getTransferAccountNumber()))
//                .transferAccountNumber(UUID.fromString(transactionDetails.getAccountNumber()))
//                .type(TransferType.TRANSFER)
//                .build();
//    }

//    private Transaction getPrimaryTransaction(TransactionDetails transactionDetails) {
//        return Transaction.builder()
//                .amount(transactionDetails.getAmount())
//                .accountNumber(UUID.fromString(transactionDetails.getAccountNumber()))
//                .transferAccountNumber(transactionDetails.getTransferAccountNumber() != null ? UUID.fromString(transactionDetails.getTransferAccountNumber()) : null)
//                .type(getTransferType(transactionDetails.getTransferAccountNumber(), transactionDetails.getAmount()))
//                .build();
//    }

//    private TransferType getTransferType(String accountNumber, Double amount) {
//        if(accountNumber==null){
//            return amount >= 0 ? TransferType.DEPOSIT : TransferType.WITHDRAW;
//        }
//        return TransferType.TRANSFER;
//    }

    public static TransactionDetails convertToTransactionPojo(Transaction transaction) {
        return TransactionDetails.builder()
                .accountNumber(transaction.getAccountNumber().toString())
                .amount(transaction.getAmount())
                .transactionID(transaction.getId().toString())
                .transactionType(transaction.getTransactionType())
                .description(transaction.getDescription())
                .build();
    }
}
