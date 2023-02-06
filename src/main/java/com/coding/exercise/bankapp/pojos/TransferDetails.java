package com.coding.exercise.bankapp.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferDetails {
    @NotNull
    private String sourceAccount;
    @NotNull
    private String targetAccount;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String description;
    @ApiModelProperty(readOnly = true)
    private UUID sourceTransactionID;

    @ApiModelProperty(readOnly = true)
    private UUID targetTransactionID;

    @ApiModelProperty(readOnly = true)
    private UUID transferId;

}
