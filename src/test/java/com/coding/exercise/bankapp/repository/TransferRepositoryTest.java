package com.coding.exercise.bankapp.repository;

import com.coding.exercise.bankapp.model.Transfer;
import com.coding.exercise.bankapp.respository.TransferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransferRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransferRepository transferRepository;

    @Test
    public void testFindBySourceTransactionID() {
        Transfer transfer = Transfer.builder()
                .sourceTransactionID(UUID.randomUUID())
                .targetTransactionID(UUID.randomUUID())
                .build();
        entityManager.persist(transfer);
        Optional<List<Transfer>> optionalAccount = transferRepository.findBySourceTransactionID(transfer.getSourceTransactionID());
        assertTrue(optionalAccount.isPresent());
        assertEquals(transfer, optionalAccount.get().get(0));
    }
}
