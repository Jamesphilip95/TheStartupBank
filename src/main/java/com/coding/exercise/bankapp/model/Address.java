package com.coding.exercise.bankapp.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue
    @Column(name="ADDRESS_ID")
    private UUID id;

    private String postcode;
    private String city;
    private String county;
    private String country;
    private String addressLine1;
    private String addressLine2;
}
