package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

        assertEquals("House",customer.getAddressDetails().getAddressLine1());
        assertEquals("Street",customer.getAddressDetails().getAddressLine2());
        assertEquals("City",customer.getAddressDetails().getCity());
        assertEquals("UK",customer.getAddressDetails().getCountry());
        assertEquals("12345",customer.getAddressDetails().getPostcode());

        assertEquals("joe.bloggs.com",customer.getContactDetails().getEmail());
        assertEquals("0123456789",customer.getContactDetails().getMobile());
    }
}
