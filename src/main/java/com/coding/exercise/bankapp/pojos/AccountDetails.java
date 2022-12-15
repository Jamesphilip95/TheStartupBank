package com.coding.exercise.bankapp.pojos;

import com.coding.exercise.bankapp.common.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
public class AccountDetails {

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String accountId;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double accountBalance;
    @NotNull
    private final BankInformationDetails bankInformationDetails;

    @Valid
    @NotNull
    @ValueOfEnum(enumClass = AccountType.class, message = "Must be valid account type. CURRENT or SAVINGS")
    private final String accountType;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long customerNumber;

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Date accountCreatedTime;

}
