package com.coding.exercise.bankapp.respository;

import com.coding.exercise.bankapp.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account,String> {
    Optional<Account> findByAccountNumber(UUID accountNumber);

    Optional<List<Account>> findByCustomerNumber(Long customerNumber);
}