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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.coding.exercise.bankapp.TheStartupBankApplication.createID;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTest {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private CustomerService customerService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testRegisterHappyPath() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        Customer customer = BaseTest.buildCustomerEntity();
        when(bankServiceHelper.convertCustomerToEntity(customerDetails)).thenReturn(customer);
        Date date = new Date();

        Long currentId = createID();

        Long customerNumber = customerService.registerCustomer(customerDetails);

        verify(customerRepository).save(customer);

        assertEquals(date.getDay(), customer.getCreateDateTime().getDay()); //toDo do this better
        assertEquals(date.getMonth(), customer.getCreateDateTime().getMonth());
        assertEquals(date.getYear(), customer.getCreateDateTime().getYear());

        assertEquals(++currentId,customerNumber);
    }

    @Test
    public void testGetCustomer() {
        Optional<Customer> customerEntityOpt = Optional.of(BaseTest.buildCustomerEntity());
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(customerDetails);

        assertEquals(customerDetails, customerService.getCustomer(12345L));
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(BaseTest.buildCustomerEntity());
        customerList.add(BaseTest.buildCustomerEntity());
        when(customerRepository.findAll()).thenReturn(customerList);
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(BaseTest.buildCustomerDetailsPayload());

        List<CustomerDetails> allCustomers = customerService.findAllCustomers();
        assertNotNull(allCustomers);
        assertEquals(2, allCustomers.size());
    }

    @Test
    public void testFindAllCustomersWhenEmptyRepository() {
        when(customerRepository.findAll()).thenReturn(new ArrayList<>());
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(BaseTest.buildCustomerDetailsPayload());

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
        Customer customer = BaseTest.buildCustomerEntity();
        when(bankServiceHelper.convertCustomerToEntity(customerDetails)).thenReturn(customer);

        customerService.registerCustomer(customerDetails);
        Long currentCustomerNumber = customer.getCustomerNumber();

        customerService.registerCustomer(customerDetails);
        assertEquals(++currentCustomerNumber,customer.getCustomerNumber());

        customerService.registerCustomer(customerDetails);
        assertEquals(++currentCustomerNumber,customer.getCustomerNumber());

        customerService.registerCustomer(customerDetails);
        assertEquals(++currentCustomerNumber,customer.getCustomerNumber());
    }
}
