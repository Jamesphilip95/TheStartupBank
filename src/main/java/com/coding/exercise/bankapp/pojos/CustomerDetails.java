package com.coding.exercise.bankapp.pojos;

import com.coding.exercise.bankapp.TheStartupBankApplication;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDetails {

    private final String firstName;
    private final String lastName;
    private final ContactDetails contactDetails;
    private final AddressDetails addressDetails;
    @JsonIgnore
    private final Long customerNumber;

}
