package com.coding.exercise.bankapp.respository;

import com.coding.exercise.bankapp.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {
    Optional<List<Transaction>> findByAccountNumber(String accountNumber);
    Optional<Transaction> findById(String accountNumber);
}
