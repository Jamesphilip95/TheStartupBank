package com.coding.exercise.bankapp.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankInformationDetails {

    private String branchName;

    private Integer branchCode;

    private AddressDetails branchAddress;

}
