package com.coding.exercise.bankapp.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContactDetails {
    private String email;
    private String mobile;

}
