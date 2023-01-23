package com.coding.exercise.bankapp;

import com.coding.exercise.bankapp.model.*;
import com.coding.exercise.bankapp.pojos.*;

import java.util.UUID;

public class BaseTest {

    public static CustomerDetails buildCustomerDetailsPayload() {
        return CustomerDetails.builder()
                .lastName("Bloggs")
                .firstName("Joe")
                .contactDetails(buildContactDetailsPayload())
                .customerNumber(12345L)
                .addressDetails(buildAddressDetailsPayload())
                .build();

    }

    static AddressDetails buildAddressDetailsPayload() {
        return AddressDetails.builder()
                .addressLine1("House")
                .addressLine2("Street")
                .city("City")
                .postcode("12345")
                .country("UK")
                .build();
    }

    static ContactDetails buildContactDetailsPayload() {
        return ContactDetails.builder()
                .email("joe.bloggs.com")
                .mobile("0123456789")
                .build();
    }

    public static Customer buildCustomerEntity() {
        return Customer.builder()
                .lastName("Bloggs")
                .firstName("Joe")
                .contactDetails(buildContactEntity())
                .customerNumber(12345L)
                .addressDetails(buildAddressEntity())
                .build();
    }

    private static Address buildAddressEntity() {
        return Address.builder()
                .addressLine1("House")
                .addressLine2("Street")
                .city("City")
                .postcode("12345")
                .country("UK")
                .build();
    }

    private static Contact buildContactEntity() {
        return Contact.builder()
                .email("joe.bloggs.com")
                .mobile("0123456789")
                .build();
    }

    public static AccountDetails buildAccountDetailsPayload() {
        return AccountDetails.builder()
                .customerNumber(12345L)
                .bankInformationDetails(buildBankInfoDetails())
                .build();
    }

    private static BankInformationDetails buildBankInfoDetails() {
        return BankInformationDetails.builder()
                .branchAddress(buildAddressDetailsPayload())
                .branchCode(909)
                .branchName("Test Bank")
                .build();
    }

    public static Account buildAccountEntity() {
        return Account.builder()
                .customerNumber(12345L)
                .bankInformation(buildBankInfo())
                .accountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }

    private static BankInformation buildBankInfo() {
        return BankInformation.builder()
                .branchAddress(buildAddressEntity())
                .branchCode(909)
                .branchName("Test Bank")
                .build();
    }

    public static Account buildAccount2Entity() {
        return Account.builder()
                .customerNumber(12345L)
                .bankInformation(buildBankInfo())
                .accountNumber(UUID.fromString("967e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }

    public static Account buildAccount3Entity() {
        return Account.builder()
                .customerNumber(12345L)
                .bankInformation(buildBankInfo())
                .accountNumber(UUID.fromString("127e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }

    public static TransactionDetails buildTransactionDetailsPayload() {
        return TransactionDetails.builder()
                .amount(40.50)
                .accountNumber("567e2712-cafe-4204-8449-2059435c24a0")
                .transactionID("127e2712-cafe-4204-8449-2059435c24a0")
                .build();
    }

    public static Transaction buildTransactionEntity() {
        return Transaction.builder()
                .amount(40.50)
                .accountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }

    public static Transaction buildTransactionEntity2() {
        return Transaction.builder()
                .amount(40.50)
                .build();
    }

    public static Transaction buildTransactionEntity3() {
        return Transaction.builder()
                .amount(40.50)
                .build();
    }

    public static Transaction buildTransactionEntityWithId() {
        return Transaction.builder()
                .amount(40.50)
                .accountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"))
                .id(UUID.fromString("127e2712-cafe-4204-8449-2059435c24a0"))
                .build();
    }
}
