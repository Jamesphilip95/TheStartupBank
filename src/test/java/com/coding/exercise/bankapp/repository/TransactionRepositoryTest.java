package com.coding.exercise.bankapp.repository;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.model.Transaction;
import com.coding.exercise.bankapp.respository.TransactionRepository;
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
public class TransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testFindByTransactionNumber() {
        Transaction transaction = BaseTest.buildTransactionEntity();
        entityManager.persist(transaction);
        Optional<Transaction> optionalAccount = transactionRepository.findById(transaction.getId());
        assertTrue(optionalAccount.isPresent());
        assertEquals(transaction, optionalAccount.get());
    }

    @Test
    public void testFindByAccountNumber() {
        Transaction transaction = BaseTest.buildTransactionEntity();
        transaction.setAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"));
        Transaction transaction2 = BaseTest.buildTransactionEntity2();
        transaction2.setAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"));
        Transaction transaction3 = BaseTest.buildTransactionEntity3();
        transaction3.setAccountNumber(UUID.fromString("247e2712-cafe-4204-8449-2059435c24a0"));
        entityManager.persist(transaction);
        entityManager.persist(transaction2);
        entityManager.persist(transaction3);

        Optional<List<Transaction>> optionalAccount = transactionRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"));
        assertTrue(optionalAccount.isPresent());
        assertEquals(2, optionalAccount.get().size());
    }
}
