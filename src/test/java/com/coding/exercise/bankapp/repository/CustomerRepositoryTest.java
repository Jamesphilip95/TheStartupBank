package com.coding.exercise.bankapp.repository;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindByCustomerNumber() {
        Customer customer = BaseTest.buildCustomerEntity();
        entityManager.persist(customer);
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerNumber(12345L);
        assertTrue(optionalCustomer.isPresent());
        assertEquals(customer, optionalCustomer.get());
    }
}
