package com.coding.exercise.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name="ACCOUNT_ID")
    private UUID accountNumber;
    private Long customerNumber;
    @OneToOne(cascade=CascadeType.ALL)
    private BankInformation bankInformation;
    private Date accountCreatedTime;


}