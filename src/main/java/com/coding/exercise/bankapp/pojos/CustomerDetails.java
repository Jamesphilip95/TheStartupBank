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
    private String mobileNumber;

    @NotNull
    private String email;

    @ApiModelProperty(readOnly = true)
    private Long customerNumber;

    @ApiModelProperty(readOnly = true)
    private Date createDateTime;

}
