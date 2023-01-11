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
    private final BankInformationDetails bankInformationDetails;
    @NotNull
    private final Long customerNumber;

    @ApiModelProperty(readOnly = true)
    private final Date accountCreatedTime;

}
