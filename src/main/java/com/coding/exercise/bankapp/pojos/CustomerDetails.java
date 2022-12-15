package com.coding.exercise.bankapp.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;



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
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long customerNumber;

    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date createDateTime;
    @ApiModelProperty(hidden = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<String> accounts;

}
