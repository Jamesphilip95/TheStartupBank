package com.coding.exercise.bankapp.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
@Getter
@Setter
@Builder
public class TransactionDetails {
    @NotNull
    private Double amount;
    @NotNull
    private String accountNumber;
    @ApiModelProperty(readOnly = true)
    private String transactionID;

}
