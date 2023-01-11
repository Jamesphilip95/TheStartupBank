package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.respository.TransactionRepository;
import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTests {

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private BankServiceHelper bankServiceHelper;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testRegisterHappyPath() {
        when(bankServiceHelper.convertCustomerToEntity(BaseTest.buildCustomerDetailsPayload())).thenReturn(new Customer());
    }
}
