package com.coding.exercise.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    @Column(name="ACCOUNT_ID")
    private UUID id;

    private String accountNumber;

    private Double accountBalance;

    @ElementCollection
    private List<String> transactions;

    private Long customerNumber;
    @OneToOne(cascade=CascadeType.ALL)
    private BankInformation bankInformation;

    private Date accountCreatedTime;


}