package com.coding.exercise.bankapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name="CUSTOMER_ID")
    private Long customerNumber;
    private String firstName;

    private String lastName;

    private String mobile;

    private String email;
    @Temporal(TemporalType.TIME)
    private Date createDateTime;

}
