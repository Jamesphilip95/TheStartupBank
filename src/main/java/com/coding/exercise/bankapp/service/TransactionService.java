package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.model.Account;
import com.coding.exercise.bankapp.model.Transaction;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.respository.AccountRepository;
import com.coding.exercise.bankapp.respository.TransactionRepository;
import com.coding.exercise.bankapp.service.helper.BankServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {
    @Autowired
    private BankServiceHelper bankServiceHelper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity<Object> createTransaction(TransactionDetails transactionDetails) {
        Optional<Account> accountEntityOpt = accountRepository.findByAccountNumber(UUID.fromString(transactionDetails.getAccountNumber()));
        if(!accountEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account with accountNumber: " + transactionDetails.getAccountNumber()); //toDo change to exception
        }
        List<Transaction> transactions = bankServiceHelper.convertToTransactionEntities(transactionDetails);
        transactionRepository.saveAll(transactions);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

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

    public ResponseEntity<Object> getTransaction(String transactionId) {
        Optional<Transaction> transactionEntityOpt = transactionRepository.findById(UUID.fromString(transactionId));
        return transactionEntityOpt.<ResponseEntity<Object>>map(transaction -> ResponseEntity.status(HttpStatus.OK).body(bankServiceHelper.convertToTransactionPojo(transaction)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Object> getTransactionsByAccountNumber(String accountNumber) {
        List<TransactionDetails> allTransactionDetails = new ArrayList<>();
        Optional<List<Transaction>> transactionEntityOpt = transactionRepository.findByAccountNumber(UUID.fromString(accountNumber));
        if(!transactionEntityOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No transactions for accountNumber: " + accountNumber);
        }
        transactionEntityOpt.get().forEach(transaction ->
                allTransactionDetails.add(bankServiceHelper.convertToTransactionPojo(transaction))
        );
        return ResponseEntity.status(HttpStatus.OK).body(allTransactionDetails);
    }

//    private boolean hasAccountAlready(String accountType, List<String> accounts) {
//        return accounts.stream()
//                .map(accountNumber -> accountRepository.findByAccountNumber(accountNumber))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .anyMatch(account -> accountType.equals(account.getAccountType()));
//    }

    //    public ResponseEntity<Object> getAccountBalance(Long customerNumber, String accountNumber) {
//        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
//        if(!customerEntityOpt.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customer with customerNumber: " + customerNumber);
//        }
//        Account account = getAccount(customerEntityOpt.get().getAccounts(), accountNumber);
//        if(account == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No account found for customer for account number " + accountNumber);
//        }
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(getAccountBalance(accountNumber));
//    }

//    public Double getAccountBalance(String accountNumber) {
//        Optional<List<Transaction>> transactionsEntityOpt = transactionRepository.findByAccountNumber(accountNumber);
//        if(!transactionsEntityOpt.isPresent()) {
//            return 0.00;
//        }
//        return calculateAccountBalance(transactionsEntityOpt.get());
////        List<Account> accountsForDeposit = getAccountForDeposit(customerEntityOpt.get().getAccounts(), accountType);
////        if(accountsForDeposit.isEmpty()) {
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid " + accountType + " account found for customerNumber " +customerNumber);
////        }
////        return ResponseEntity.status(HttpStatus.OK)
////                .body(accountsForDeposit.get(0).getAccountBalance());
//    }

//    private Double calculateAccountBalance(List<Transaction> transactions) {
//        Double balance = 0.00;
//        for (Transaction transaction : transactions) {
//            switch (transaction.getType()) {
//                case DEPOSIT:
//                    balance += transaction.getAmount();
//                    break;
//                case WITHDRAW:
//                    balance -= transaction.getAmount();
//                    break;
//            }
//        }
//        return balance;
//    }
}
