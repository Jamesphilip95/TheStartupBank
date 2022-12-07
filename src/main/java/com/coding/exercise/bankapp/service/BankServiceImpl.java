package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankServiceHelper bankServiceHelper;


    @Override
    public ResponseEntity<Object> findAll() {

        List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer -> {
            allCustomerDetails.add(bankServiceHelper.convertToCustomerPojo(customer));
        });

        return ResponseEntity.status(HttpStatus.OK).body(allCustomerDetails);
    }

    @Override
    public ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails) {

        Customer customer = bankServiceHelper.convertCustomerToEntity(customerDetails);
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer number: " + customer.getCustomerNumber());
    }
}
