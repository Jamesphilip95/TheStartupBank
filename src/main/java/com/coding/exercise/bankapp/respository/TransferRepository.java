package com.coding.exercise.bankapp.respository;

import com.coding.exercise.bankapp.model.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, UUID> {
    Optional<List<Transfer>> findBySourceTransactionID(UUID sourceTransactionId);
    Optional<List<Transfer>> findByTargetTransactionID(UUID targetTransactionId);
}
