package com.coding.exercise.bankapp.pojos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ContactDetails {
    private final String email;
    private final String mobile;

}
