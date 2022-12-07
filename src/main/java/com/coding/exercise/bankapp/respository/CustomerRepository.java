package com.coding.exercise.bankapp.respository;

import com.coding.exercise.bankapp.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,String> {
    Optional<Customer> findByCustomerNumber(Long customerNumber);
}
