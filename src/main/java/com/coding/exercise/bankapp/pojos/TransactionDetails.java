package com.coding.exercise.bankapp.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Builder
public class TransactionDetails {
    @NotNull
    private final Double amount;
    @NotNull
    private final String accountNumber;
//    private final String transferAccountNumber;
//    @ApiModelProperty(readOnly = true)
//    private final TransferType type;
    @ApiModelProperty(readOnly = true)
    private final String transactionID;

}
