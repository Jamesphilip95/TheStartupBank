package com.coding.exercise.bankapp.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDetails {
    private String postcode;
    private String city;
    private String county;
    private String country;
    private String addressLine1;
    private String addressLine2;
}
