package com.coding.exercise.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BankInformation {

    @Id
    @GeneratedValue
    @Column(name="BANK_INFO_ID")
    private UUID id;

    private String branchName;

    private Integer branchCode;

    @OneToOne(cascade=CascadeType.ALL)
    private Address branchAddress;
}