package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.pojos.TransactionType;
import com.coding.exercise.bankapp.pojos.TransferDetails;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BankServiceHelperTest {

    @Test
    public void testConvertCustomerToEntity() {
        Customer customer = BankServiceHelper.convertCustomerToEntity(BaseTest.buildCustomerDetailsPayload());
        assertEquals("Joe",customer.getFirstName());
        assertEquals("Bloggs",customer.getLastName());
        assertEquals(Long.valueOf(12345),customer.getCustomerNumber());

        assertEquals("joe.bloggs.com", customer.getEmail());
        assertEquals("0123456789", customer.getMobile());
    }

    @Test
    public void testCreateSourceTransaction() {
        TransferDetails transferDetails = BaseTest.buildTransferDetailsPayload();
        TransactionDetails transactionDetails = BankServiceHelper.buildSourceTransaction(transferDetails);
        assertEquals(TransactionType.TRANSFER, transactionDetails.getTransactionType());
        assertEquals("Cricket", transactionDetails.getDescription());
        assertEquals(BigDecimal.valueOf(-40.50), transactionDetails.getAmount());
        assertEquals("567e2712-cafe-4204-8449-2059435c24a0", transactionDetails.getAccountNumber());
    }

    @Test
    public void testCreateTargetTransaction() {
        TransferDetails transferDetails = BaseTest.buildTransferDetailsPayload();
        TransactionDetails transactionDetails = BankServiceHelper.buildTargetTransaction(transferDetails);
        assertEquals(TransactionType.TRANSFER, transactionDetails.getTransactionType());
        assertEquals("Cricket", transactionDetails.getDescription());
        assertEquals(BigDecimal.valueOf(40.50), transactionDetails.getAmount());
        assertEquals("127e2712-cafe-4204-8449-2059435c24a0", transactionDetails.getAccountNumber());
    }
}
