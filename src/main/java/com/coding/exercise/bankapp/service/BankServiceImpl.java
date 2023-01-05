package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.model.Transaction;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.CustomerRepository;
import com.coding.exercise.bankapp.respository.TransactionRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.coding.exercise.bankapp.TheStartupBankApplication.createID;

@Service
public class BankServiceImpl implements BankService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


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
    public ResponseEntity<Object> findAccounts(Long customerNumber) {
        if(customerNumber!= null) {
            return findAccountsByCustomerNumber(customerNumber);
        }
        Iterable<Account> accountList = accountRepository.findAll();
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        accountList.forEach(account ->
                allAccountDetails.add(bankServiceHelper.convertToAccountPojo(account))
        );

        return ResponseEntity.status(HttpStatus.OK).body(allAccountDetails);
    }

    private ResponseEntity<Object> findAccountsByCustomerNumber(Long customerNumber) {
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
        List<String> accounts = customerEntityOpt.get().getAccounts();
        accounts.forEach( accountNumber -> allAccountDetails.add(findByAccountNumber(accountNumber)));
        return ResponseEntity.status(HttpStatus.OK).body(allAccountDetails);
    }

    private AccountDetails findByAccountNumber(String accountNumber) {
        Optional<Account> customerEntityOpt = accountRepository.findByAccountNumber(accountNumber);

        return customerEntityOpt.map(account -> bankServiceHelper.convertToAccountPojo(account)).orElse(null);
    }

//    public CustomerDetails findByCustomerNumber(Long customerNumber) {
//        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
//
//        return customerEntityOpt.map(customer -> bankServiceHelper.convertToCustomerPojo(customer)).orElse(null);
//    }
    private void addTransactionToAccount(UUID id, Account account) {
        if(account.getTransactions() != null) {
            account.getTransactions().add(id.toString());
        }
        else {
            List<String> transaction = new ArrayList<>();
            transaction.add(id.toString());
            account.setTransactions(transaction);
        }
        account.setAccountBalance(getAccountBalance(account.getAccountNumber()));
    }

    private Account getAccount(List<String> accounts, String accountNumber) {
        if(accounts.contains(accountNumber)){
            Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(accountNumber);
            if(accountEntityOpt.isPresent()) {
                return accountEntityOpt.get();
            }
        }
        return null;
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
    public ResponseEntity<Object> getAccountBalance(Long customerNumber, String accountNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
        Account account = getAccount(customerEntityOpt.get().getAccounts(), accountNumber);
        if(account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found for customer for account number " + accountNumber);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(getAccountBalance(accountNumber));
    }

    public Double getAccountBalance(String accountNumber) {
        Optional<List<Transaction>> transactionsEntityOpt = transactionRepository.findByAccountNumber(accountNumber);
        if(!transactionsEntityOpt.isPresent()) {
            return 0.00;
        }
        return calculateAccountBalance(transactionsEntityOpt.get());
//        List<Account> accountsForDeposit = getAccountForDeposit(customerEntityOpt.get().getAccounts(), accountType);
//        if(accountsForDeposit.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid " + accountType + " account found for customerNumber " +customerNumber);
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(accountsForDeposit.get(0).getAccountBalance());
    }

    private Double calculateAccountBalance(List<Transaction> transactions) {
        Double balance = 0.00;
        for (Transaction transaction : transactions) {
            switch (transaction.getType()) {
                case DEPOSIT:
                    balance += transaction.getAmount();
                    break;
                case WITHDRAW:
                    balance -= transaction.getAmount();
                    break;
            }
        }
        return balance;
    }

    @Override
    public ResponseEntity<Object> depositMoney(Long customerNumber, TransactionDetails transactionDetails) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
//        if(customerDetails.getAccounts().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts for customer: " +customerNumber);
//        }
//        List<Account> accounts = getAccountForDeposit(customerDetails.getAccounts(), transferDetails.getAccountNumber());
//        if(accounts.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid " + transferDetails.getAccountType() + " account found for customerNumber " +customerNumber);
//        }
        Account account = getAccount(customerEntityOpt.get().getAccounts(), transactionDetails.getAccountNumber());
        if(account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found for customer with account number " + transactionDetails.getAccountNumber());
        }
        Transaction deposit = bankServiceHelper.convertToDepositEntity(customerNumber, transactionDetails);
        transactionRepository.save(deposit);
        addTransactionToAccount(deposit.getId(), account);
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("Depositing £" + transactionDetails.getAmount());
    }


    @Override
    public ResponseEntity<Object> withdrawMoney(Long customerNumber, TransactionDetails transactionDetails) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
//        if(customerDetails.getAccounts().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No accounts for customer: " +customerNumber);
//        }
        Account account = getAccount(customerEntityOpt.get().getAccounts(), transactionDetails.getAccountNumber());
        if(account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found for customer for account number " + transactionDetails.getAccountNumber());
        }
        double newBalance = getAccountBalance(account.getAccountNumber()) - transactionDetails.getAmount();
        if(newBalance<0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No enough funds in account to withdraw requested amount");
        }
        Transaction withdraw = bankServiceHelper.convertToWithdrawEntity(customerNumber, transactionDetails);
        transactionRepository.save(withdraw);
        addTransactionToAccount(withdraw.getId(), account);
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("Expensing £" + transactionDetails.getAmount());
    }

    @Override
    public ResponseEntity<Object> getTransactions(String accountNumber) {
        if(accountNumber!=null) {
            return getTransactionsByAccountNumber(accountNumber);
        }
        List<TransactionDetails> allTransactionDetails = new ArrayList<>();
        Iterable<Transaction> transactionList = transactionRepository.findAll();

        transactionList.forEach(transaction ->
                allTransactionDetails.add(bankServiceHelper.convertToTransactionPojo(transaction))
        );

        return ResponseEntity.status(HttpStatus.OK).body(allTransactionDetails);
    }

    @Override
    public ResponseEntity<Object> getTransaction(String transactionId) {
        Optional<Transaction> transactionEntityOpt = transactionRepository.findById(transactionId);
        if(!transactionEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transaction with transactionId: " + transactionId);
        }
        return ResponseEntity.status(HttpStatus.OK).body(bankServiceHelper.convertToTransactionPojo(transactionEntityOpt.get()));
    }

    public ResponseEntity<Object> getTransactionsByAccountNumber(String accountNumber) {
        List<TransactionDetails> allTransactionDetails = new ArrayList<>();
        Optional<List<Transaction>> transactionEntityOpt = transactionRepository.findByAccountNumber(accountNumber);
        if(!transactionEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions for accountNumber: " + accountNumber);
        }
        transactionEntityOpt.get().forEach(transaction ->
                allTransactionDetails.add(bankServiceHelper.convertToTransactionPojo(transaction))
        );
        return ResponseEntity.status(HttpStatus.OK).body(allTransactionDetails);
    }

    @Override
    public ResponseEntity<Object> addAccount(AccountDetails accountDetails, Long customerNumber) {
        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if(!customerEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
        }
//        if(hasAccountAlready(accountDetails.getAccountType(), customerEntityOpt.get().getAccounts())) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer already has " + accountDetails.getAccountType() + " account");
//        }
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

//    private boolean hasAccountAlready(String accountType, List<String> accounts) {
//        return accounts.stream()
//                .map(accountNumber -> accountRepository.findByAccountNumber(accountNumber))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .anyMatch(account -> accountType.equals(account.getAccountType()));
//    }

    private void addAccountToCustomerRepository(String accountNumber, Customer customer) {
        customer.getAccounts().add(accountNumber);
        customerRepository.save(customer);
    }
}
