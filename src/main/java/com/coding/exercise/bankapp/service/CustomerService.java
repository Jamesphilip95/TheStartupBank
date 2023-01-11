package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.coding.exercise.bankapp.TheStartupBankApplication.createID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankServiceHelper bankServiceHelper;

    public ResponseEntity<Object> findAllCustomers() {
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();
        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer ->
                allCustomerDetails.add(bankServiceHelper.convertToCustomerPojo(customer))
        );

        return ResponseEntity.status(HttpStatus.OK).body(allCustomerDetails);
    }

    public ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails) {
        Customer customer = bankServiceHelper.convertCustomerToEntity(customerDetails);
        customer.setCustomerNumber(createID());
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<Object> getCustomer(Long customerNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        return customerEntityOpt.<ResponseEntity<Object>>map(customer ->
                ResponseEntity.status(HttpStatus.OK).body(bankServiceHelper.convertToCustomerPojo(customer)))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
