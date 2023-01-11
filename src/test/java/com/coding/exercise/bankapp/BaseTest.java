package com.coding.exercise.bankapp;

import com.coding.exercise.bankapp.pojos.AddressDetails;
import com.coding.exercise.bankapp.pojos.ContactDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;

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
}
