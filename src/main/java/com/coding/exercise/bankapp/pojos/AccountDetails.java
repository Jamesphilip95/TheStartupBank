package com.coding.exercise.bankapp.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class AccountDetails {

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String accountId;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Double accountBalance;
    private final BankInformationDetails bankInformationDetails;

    private final String accountType; //toDo create an Enum for account type. Validation as well

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long customerNumber;

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Date accountCreatedTime;

}
