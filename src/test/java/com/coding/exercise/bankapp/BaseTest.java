package com.coding.exercise.bankapp;

import com.coding.exercise.bankapp.model.Address;
import com.coding.exercise.bankapp.model.Contact;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.*;

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
}
