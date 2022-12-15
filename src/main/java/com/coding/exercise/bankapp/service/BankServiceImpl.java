package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.pojos.TransferDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.coding.exercise.bankapp.TheStartupBankApplication.createID;

@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public ResponseEntity<Object> findAllCustomers() {
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();
        Iterable<Customer> customerList = customerRepository.findAll();

        customerList.forEach(customer ->
            allCustomerDetails.add(bankServiceHelper.convertToCustomerPojo(customer))
        );

        return ResponseEntity.status(HttpStatus.OK).body(allCustomerDetails);
    }

    @Override
    public ResponseEntity<Object> registerCustomer(CustomerDetails customerDetails) {
        Customer customer = bankServiceHelper.convertCustomerToEntity(customerDetails);
        customer.setCustomerNumber(createID());
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer number: " + customer.getCustomerNumber());
    }

    @Override
    public ResponseEntity<Object> findAllAccountsForCustomer(Long customerNumber) {
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        CustomerDetails customerDetails = findByCustomerNumber(customerNumber);
        if(customerDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<String> accounts = customerDetails.getAccounts();
        accounts.forEach( accountNumber -> allAccountDetails.add(findByAccountNumber(accountNumber)));
        return ResponseEntity.status(HttpStatus.OK).body(allAccountDetails);
    }

    private AccountDetails findByAccountNumber(String accountNumber) {
        Optional<Account> customerEntityOpt = accountRepository.findByAccountNumber(accountNumber);

        return customerEntityOpt.map(account -> bankServiceHelper.convertToAccountPojo(account)).orElse(null);
    }

    public CustomerDetails findByCustomerNumber(Long customerNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        return customerEntityOpt.map(customer -> bankServiceHelper.convertToCustomerPojo(customer)).orElse(null);
    }

    @Override
    public ResponseEntity<Object> depositMoney(Long customerNumber, TransferDetails transferDetails) {
        CustomerDetails customerDetails = findByCustomerNumber(customerNumber);
        if(customerDetails == null) { //ToDo create proper exceptions
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(customerDetails.getAccounts().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts for customer: " +customerNumber);
        }
        List<Account> accounts = getAccountForDeposit(customerDetails.getAccounts(), transferDetails.getAccountType());
        if(accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid " + transferDetails.getAccountType() + " account found for customerNumber " +customerNumber);
        }
        accounts.get(0).setAccountBalance(accounts.get(0).getAccountBalance() + transferDetails.getAmount());
        accountRepository.save(accounts.get(0));
        //ToDo deposit save to transactions
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Object> getCustomer(Long customerNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
        return ResponseEntity.status(HttpStatus.OK).body(bankServiceHelper.convertToCustomerPojo(customerEntityOpt.get()));
    }

    @Override
    public ResponseEntity<Object> getAccountBalance(Long customerNumber, String accountType) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
        List<Account> accountsForDeposit = getAccountForDeposit(customerEntityOpt.get().getAccounts(), accountType);
        if(accountsForDeposit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid " + accountType + " account found for customerNumber " +customerNumber);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsForDeposit.get(0).getAccountBalance());
    }

    @Override
    public ResponseEntity<Object> withdrawMoney(Long customerNumber, TransferDetails transferDetails) {
        CustomerDetails customerDetails = findByCustomerNumber(customerNumber);
        if(customerDetails == null) { //ToDo create proper exceptions
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(customerDetails.getAccounts().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts for customer: " +customerNumber);
        }
        List<Account> accounts = getAccountForDeposit(customerDetails.getAccounts(), transferDetails.getAccountType());
        if(accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid " + transferDetails.getAccountType() + " account found for customerNumber " +customerNumber);
        }
        Account account = accounts.get(0); //toDo make stream better
        double newBalance = account.getAccountBalance() - transferDetails.getAmount();
        if(newBalance<0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No enough funds in account to withdraw requested amount");
        }
        account.setAccountBalance(newBalance);
        accountRepository.save(account);
        //ToDo deposit save to transactions
        return ResponseEntity.status(HttpStatus.CREATED).body("Expensing £" + transferDetails.getAmount());
    }

    private List<Account> getAccountForDeposit(List<String> accounts, String accountType) {
       return accounts.stream()
               .map(accountNumber -> accountRepository.findByAccountNumber(accountNumber))
               .filter(Optional::isPresent)
               .map(Optional::get)
               .filter(account -> accountType.equals(account.getAccountType())).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> addAccount(AccountDetails accountDetails, Long customerNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
        if(hasAccountAlready(accountDetails.getAccountType(), customerEntityOpt.get().getAccounts())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer already has " + accountDetails.getAccountType() + " account");
        }
        Account account = bankServiceHelper.convertAccountToEntity(accountDetails);
        account.setAccountBalance(0.00);
        account.setAccountCreatedTime(new Date());
        account.setAccountNumber(String.valueOf(UUID.randomUUID()));
        account.setCustomerNumber(customerNumber);
        accountRepository.save(account);
        addAccountToCustomerRepository(account.getAccountNumber(), customerEntityOpt.get());
        //toDo add logging
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Id: " + account.getAccountNumber());
    }

    private boolean hasAccountAlready(String accountType, List<String> accounts) {
        return accounts.stream()
                .map(accountNumber -> accountRepository.findByAccountNumber(accountNumber))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(account -> accountType.equals(account.getAccountType()));
    }

    private void addAccountToCustomerRepository(String accountNumber, Customer customer) {
        customer.getAccounts().add(accountNumber);
        customerRepository.save(customer);
    }
}
