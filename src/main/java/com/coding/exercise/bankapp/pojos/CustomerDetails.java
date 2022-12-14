package com.coding.exercise.bankapp.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;


@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDetails {


    private final String firstName;
    @NotNull
    private final String lastName;
    @NotNull
    private final ContactDetails contactDetails;
    @NotNull
    private final AddressDetails addressDetails;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long customerNumber;

    @JsonIgnore
    private final List<AccountDetails> accountDetails;

}
