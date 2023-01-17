package com.coding.exercise.bankapp.repository;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.respository.AccountRepository;
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
public class AccountRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindByAccountNumber() {
        Account account = BaseTest.buildAccountEntity();
        entityManager.persist(account);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(UUID.fromString("567e2712-cafe-4204-8449-2059435c24a0"));
        assertTrue(optionalAccount.isPresent());
        assertEquals(account, optionalAccount.get());
    }

    @Test
    public void testFindByCustomerNumber() {
        Account account1 = BaseTest.buildAccountEntity();
        account1.setCustomerNumber(123456L);
        Account account2 = BaseTest.buildAccount2Entity();
        account2.setCustomerNumber(123456L);
        Account account3 = BaseTest.buildAccount3Entity();
        account3.setCustomerNumber(54321L);
        entityManager.persist(account1);
        entityManager.persist(account2);
        entityManager.persist(account3);

        Optional<List<Account>> optionalAccount = accountRepository.findByCustomerNumber(123456L);
        assertTrue(optionalAccount.isPresent());
        assertEquals(2, optionalAccount.get().size());
    }
}
