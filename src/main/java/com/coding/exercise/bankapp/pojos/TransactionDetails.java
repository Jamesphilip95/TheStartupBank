package com.coding.exercise.bankapp.pojos;

import lombok.*;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Builder
public class TransactionDetails {
//    @Valid
//    @NotNull
//    @ValueOfEnum(enumClass = AccountType.class, message = "Must be valid account type. DEPOSIT or SAVINGS")
//    @JsonIgnore
//    private final String type;
    @NotNull
    private final Double amount;

    @NotNull
    private final String accountNumber;

    private final String transferAccountNumber;

    private final TransferType type;

    private final Long customerNumber;

}
