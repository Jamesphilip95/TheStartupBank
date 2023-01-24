package com.coding.exercise.bankapp.service;


import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;
    @Captor
    ArgumentCaptor<Customer> customerCaptor;
    @Autowired
    private CustomerService customerService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testRegisterHappyPath() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        Long customerNumber = customerService.registerCustomer(customerDetails);

        verify(customerRepository).save(customerCaptor.capture());

        Customer actualCustomer = customerCaptor.getValue();
        Customer customer = BankServiceHelper.convertCustomerToEntity(customerDetails);
        customer.setCustomerNumber(actualCustomer.getCustomerNumber());
        customer.setCreateDateTime(actualCustomer.getCreateDateTime());

        assertEquals(customer, actualCustomer);
        assertEquals(customerNumber, actualCustomer.getCustomerNumber());
    }

    @Test
    public void testGetCustomer() {
        Customer customer = BaseTest.buildCustomerEntity();
        Optional<Customer> customerEntityOpt = Optional.of(customer);
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        CustomerDetails customerDetails = BankServiceHelper.convertToCustomerPojo(customerEntityOpt.get());

        CustomerDetails actualCustomerDetails = customerService.getCustomer(12345L);
        assertEquals(customerDetails.getCustomerNumber(),actualCustomerDetails.getCustomerNumber());
        assertEquals(customerDetails.getLastName(),actualCustomerDetails.getLastName());
        assertEquals(customerDetails.getFirstName(),actualCustomerDetails.getFirstName());
        assertEquals(customerDetails.getCreateDateTime(),actualCustomerDetails.getCreateDateTime());
        assertEquals(customerDetails.getEmail(), actualCustomerDetails.getEmail());
        assertEquals(customerDetails.getMobileNumber(), actualCustomerDetails.getMobileNumber());
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        customerList.add(BaseTest.buildCustomerEntity());
        customerList.add(BaseTest.buildCustomerEntity());
        when(customerRepository.findAll()).thenReturn(customerList);

        customerList.forEach(customer ->
                allCustomerDetails.add(BankServiceHelper.convertToCustomerPojo(customer))
        );
        List<CustomerDetails> actualAllCustomerDetails = customerService.findAllCustomers();
        assertNotNull(actualAllCustomerDetails);
        assertEquals(allCustomerDetails.size(), actualAllCustomerDetails.size());
        assertEquals(allCustomerDetails.get(0).getCustomerNumber(), actualAllCustomerDetails.get(0).getCustomerNumber());
        assertEquals(allCustomerDetails.get(1).getCustomerNumber(), actualAllCustomerDetails.get(1).getCustomerNumber());
    }

    @Test
    public void testFindAllCustomersWhenEmptyRepository() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        List<CustomerDetails> allCustomers = customerService.findAllCustomers();
        assertNotNull(allCustomers);
        assertTrue(allCustomers.isEmpty());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void testGetCustomerNotFound() {
        when(customerRepository.findByCustomerNumber(54321L)).thenReturn(Optional.empty());
        customerService.getCustomer(54321L);
        String expectedMessage = "No customer with customerNumber: 54321";
        exceptionRule.expectMessage(expectedMessage);
    }

    @Test
    public void testCustomerNumberIncrement() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();

        Long currentCustomerNumber = BankServiceHelper.createCustomerNumber();

        Long actualCustomerNumber = customerService.registerCustomer(customerDetails);
        assertEquals(++currentCustomerNumber,actualCustomerNumber);

        actualCustomerNumber = customerService.registerCustomer(customerDetails);
        assertEquals(++currentCustomerNumber,actualCustomerNumber);

        actualCustomerNumber = customerService.registerCustomer(customerDetails);
        assertEquals(++currentCustomerNumber,actualCustomerNumber);
    }
}
