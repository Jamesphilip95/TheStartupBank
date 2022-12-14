package com.coding.exercise.bankapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @Column(name="CUSTOMER_ID")
    private UUID id;

    private String firstName;

    private String lastName;

    private Long customerNumber;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Account> accounts;

    @OneToOne(cascade=CascadeType.ALL)
    private Address address;

    @OneToOne(cascade=CascadeType.ALL)
    private Contact contactDetails;

    @Temporal(TemporalType.TIME)
    private Date createDateTime;

    @Temporal(TemporalType.TIME)
    private Date updateDateTime;

}
