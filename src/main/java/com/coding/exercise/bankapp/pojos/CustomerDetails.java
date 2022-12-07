package com.coding.exercise.bankapp.pojos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CustomerDetails {

    private final String firstName;
    private final String lastName;
    private final Long customerNumber;
    private final ContactDetails contactDetails;
    private final AddressDetails addressDetails;


}
