package com.coding.exercise.bankapp.model;

import com.coding.exercise.bankapp.pojos.TransferType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name="TRANSACTION_ID")
    private UUID id;

    private TransferType type;

    private Double amount;

    private String accountNumber;

    private Long customerNumber;

    private String transferAccountNumber;
}
