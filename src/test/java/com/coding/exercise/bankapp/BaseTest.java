package com.coding.exercise.bankapp;

import com.coding.exercise.bankapp.model.*;
import com.coding.exercise.bankapp.pojos.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class BaseTest {

    public static CustomerDetails buildCustomerDetailsPayload() {
        return CustomerDetails.builder()
                .lastName("Bloggs")
                .firstName("Joe")
                .customerNumber(12345L)
                .email("joe.bloggs.com")
                .mobileNumber("0123456789")
                .build();

    }

    public static Customer buildCustomerEntity() {
        return Customer.builder()
                .lastName("Bloggs")
                .firstName("Joe")
                .customerNumber(12345L)
                .email("joe.bloggs.com")
                .mobile("0123456789")
                .build();
    }

    public static AccountDetails buildAccountDetailsPayload() {
        return AccountDetails.builder()
                .customerNumber(12345L)
                .build();
    }

    public static Account buildAccountEntity() {
        return Account.builder()
                .customerNumber(12345L)
                .accountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))
                .accountCreatedTime(new Date())
                .build();
    }

    public static Account buildAccount2Entity() {
        return Account.builder()
                .customerNumber(12345L)
                .accountNumber(UUID.fromString("967e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }

    public static Account buildAccount3Entity() {
        return Account.builder()
                .customerNumber(12345L)
                .accountNumber(UUID.fromString("127e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }

    public static TransactionDetails buildTransactionDetailsPayload() {
        return TransactionDetails.builder()
                .amount(BigDecimal.valueOf(40.50))
                .accountNumber("567e2712-cafe-4204-8449-2059435c24a0")
                .transactionID("127e2712-cafe-4204-8449-2059435c24a0")
                .transactionType(TransactionType.CASH)
                .description("Cricket")
                .build();
    }

    public static Transaction buildTransactionEntity() {
        return Transaction.builder()
                .amount(BigDecimal.valueOf(40.50))
                .accountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))
                .transactionType(TransactionType.CASH)
                .description("Cricket")
                .build();
    }

    public static Transaction buildTransactionEntity2() {
        return Transaction.builder()
                .amount(BigDecimal.valueOf(40.50))
                .transactionType(TransactionType.CASH)
                .description("Cricket")
                .build();
    }

    public static Transaction buildTransactionEntity3() {
        return Transaction.builder()
                .amount(BigDecimal.valueOf(40.50))
                .transactionType(TransactionType.CASH)
                .description("Cricket")
                .build();
    }

    public static Transaction buildTransactionEntityWithId() {
        return Transaction.builder()
                .amount(BigDecimal.valueOf(40.50))
                .accountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))
                .id(UUID.fromString("127e2712-cafe-4204-8449-2059435c24a0"))
                .description("Cricket")
                .build();
    }

    public static TransferDetails buildTransferDetailsPayload() {
        return TransferDetails.builder()
                .amount(BigDecimal.valueOf(40.50))
                .sourceAccount("567e2712-cafe-4204-8449-2059435c24a0")
                .targetAccount("127e2712-cafe-4204-8449-2059435c24a0")
                .description("Cricket")
                .build();
    }

    public static Transfer buildTransferEntity() {
        return Transfer.builder()
                .sourceTransactionID(UUID.fromString("677e2712-cafe-4204-8449-2059435c24a0"))
                .targetTransactionID(UUID.fromString("197e2712-cafe-4204-8449-2059435c24a0"))
                .id(UUID.fromString("147e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }
}
