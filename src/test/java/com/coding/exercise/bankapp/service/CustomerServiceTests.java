package com.coding.exercise.bankapp.service;


import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTests {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private CustomerService customerService;

    @Test
    public void testRegisterHappyPath() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        Customer customer = BaseTest.buildCustomerEntity();
        when(bankServiceHelper.convertCustomerToEntity(customerDetails)).thenReturn(customer);

        ResponseEntity<Object> objectResponseEntity = customerService.registerCustomer(customerDetails);

        verify(customerRepository).save(customer);
        assertEquals(201, objectResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testGetCustomer() {
        Optional<Customer> customerEntityOpt = Optional.of(BaseTest.buildCustomerEntity());
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(BaseTest.buildCustomerDetailsPayload());

        ResponseEntity<Object> customerResponseEntity = customerService.getCustomer(12345L);
        assertTrue(customerResponseEntity.getBody() instanceof CustomerDetails);
        assertEquals(200, customerResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(BaseTest.buildCustomerEntity());
        customerList.add(BaseTest.buildCustomerEntity());
        when(customerRepository.findAll()).thenReturn(customerList);
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(BaseTest.buildCustomerDetailsPayload());

        ResponseEntity<Object> customerResponseEntity = customerService.findAllCustomers();
        assertTrue(customerResponseEntity.getBody() instanceof List<?>);
        List<CustomerDetails> body = (List<CustomerDetails>) customerResponseEntity.getBody();
        assertEquals(2, body.size() );
        assertEquals(200, customerResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testFindAllCustomersWhenEmptyRepository() {
        List<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(BaseTest.buildCustomerDetailsPayload());

        ResponseEntity<Object> customersResponseEntity = customerService.findAllCustomers();
        assertTrue(customersResponseEntity.getBody() instanceof List<?>);
        List<CustomerDetails> body = (List<CustomerDetails>) customersResponseEntity.getBody();
        assertEquals(0, body.size() );
        assertEquals(200, customersResponseEntity.getStatusCodeValue());
    }
    @Test
    public void testGetCustomerNotFound() {
        Optional<Customer> customerEntityOpt = Optional.of(BaseTest.buildCustomerEntity());
        when(customerRepository.findByCustomerNumber(12345L)).thenReturn(customerEntityOpt);
        when(bankServiceHelper.convertToCustomerPojo(any())).thenReturn(BaseTest.buildCustomerDetailsPayload());

        ResponseEntity<Object> customerResponseEntity = customerService.getCustomer(54321L);
        assertEquals(404, customerResponseEntity.getStatusCodeValue());
    }

    @Test
    public void testCustomerNumberIncrement() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        Customer customer = BaseTest.buildCustomerEntity();
        when(bankServiceHelper.convertCustomerToEntity(customerDetails)).thenReturn(customer);

        customerService.registerCustomer(customerDetails);
        assertEquals(Long.valueOf(1),customer.getCustomerNumber());

        customerService.registerCustomer(customerDetails);
        assertEquals(Long.valueOf(2),customer.getCustomerNumber());

        customerService.registerCustomer(customerDetails);
        assertEquals(Long.valueOf(3),customer.getCustomerNumber());
    }

    @Test
    public void testCreatedDateAdded() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        Customer customer = BaseTest.buildCustomerEntity();
        when(bankServiceHelper.convertCustomerToEntity(customerDetails)).thenReturn(customer);
        Date date = new Date();

        customerService.registerCustomer(customerDetails);


        assertEquals(date.getDay(), customer.getCreateDateTime().getDay()); //toDo do this better
        assertEquals(date.getMonth(), customer.getCreateDateTime().getMonth());
        assertEquals(date.getYear(), customer.getCreateDateTime().getYear());
    }
}
