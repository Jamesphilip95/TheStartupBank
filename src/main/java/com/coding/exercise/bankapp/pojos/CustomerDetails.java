package com.coding.exercise.bankapp.pojos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
public class CustomerDetails {

    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private ContactDetails contactDetails;
    @NotNull
    private AddressDetails addressDetails;
    @ApiModelProperty(readOnly = true)
    private final Long customerNumber;

    @ApiModelProperty(readOnly = true)
    private final Date createDateTime;

}
