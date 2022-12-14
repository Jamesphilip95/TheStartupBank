package com.coding.exercise.bankapp.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class BankInformationDetails {

    private final String branchName;

    private final Integer branchCode;

    private final AddressDetails branchAddress;

}
