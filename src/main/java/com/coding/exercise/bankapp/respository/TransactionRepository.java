package com.coding.exercise.bankapp.respository;

import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    Optional<Customer> findByTransactionNumber(String accountNumber);
}
