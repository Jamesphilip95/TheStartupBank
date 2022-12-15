package com.coding.exercise.bankapp.pojos;

import com.coding.exercise.bankapp.common.ValueOfEnum;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
@Getter
@Setter
@Builder
public class TransferDetails {
    @Valid
    @NotNull
    @ValueOfEnum(enumClass = AccountType.class, message = "Must be valid account type. CURRENT or SAVINGS")
    private final String accountType;
    @NotNull
    private final Double amount;
}
