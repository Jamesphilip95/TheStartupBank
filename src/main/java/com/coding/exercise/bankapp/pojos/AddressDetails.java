package com.coding.exercise.bankapp.pojos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AddressDetails {
    private final String postcode;
    private final String city;
    private final String county;
    private final String country;
    private final String addressLine1;
    private final String addressLine2;
}
