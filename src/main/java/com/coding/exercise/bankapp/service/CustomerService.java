package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<CustomerDetails> findAllCustomers() {
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();
        Iterable<Customer> customerList = customerRepository.findAll();
        customerList.forEach(customer ->
                allCustomerDetails.add(bankServiceHelper.convertToCustomerPojo(customer))
        );
        return allCustomerDetails;
    }

    public Long registerCustomer(CustomerDetails customerDetails) {
        Customer customer = bankServiceHelper.convertCustomerToEntity(customerDetails);
        customer.setCustomerNumber(createID());
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);
        return customer.getCustomerNumber();
    }

    public CustomerDetails getCustomer(Long customerNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()){
            throw new ResourceNotFoundException("No customer with customerNumber: " + customerNumber);
        }
        return bankServiceHelper.convertToCustomerPojo(customerEntityOpt.get());
    }
}
