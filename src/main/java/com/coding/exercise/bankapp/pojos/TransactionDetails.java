package com.coding.exercise.bankapp.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TransactionDetails {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String accountNumber;
    @NotNull
    private TransactionType transactionType;
    @ApiModelProperty(readOnly = true)
    private String description;
    @ApiModelProperty(readOnly = true)
    private String transactionID;

}
