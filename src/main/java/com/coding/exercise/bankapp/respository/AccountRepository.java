package com.coding.exercise.bankapp.respository;

import com.coding.exercise.bankapp.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account,String> {
    Optional<Account> findByAccountNumber(String accountNumber);
}