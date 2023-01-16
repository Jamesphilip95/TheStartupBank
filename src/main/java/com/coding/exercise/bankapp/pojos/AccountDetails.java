package com.coding.exercise.bankapp.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AccountDetails {

    @ApiModelProperty(readOnly = true)
    private final UUID accountNumber;

    @NotNull
    private BankInformationDetails bankInformationDetails;
    @NotNull
    private Long customerNumber;

    @ApiModelProperty(readOnly = true)
    private final Date accountCreatedTime;

}
