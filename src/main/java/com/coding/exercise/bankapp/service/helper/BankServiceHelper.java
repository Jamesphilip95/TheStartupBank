package com.coding.exercise.bankapp.service.helper;

import com.coding.exercise.bankapp.model.*;
import com.coding.exercise.bankapp.pojos.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BankServiceHelper { //toDo change to static methods
    public Customer convertCustomerToEntity(CustomerDetails customerDetails) {
        return Customer.builder()
                .firstName(customerDetails.getFirstName())
                .lastName(customerDetails.getLastName())
                .customerNumber(customerDetails.getCustomerNumber())
                .createDateTime(customerDetails.getCreateDateTime())
                .contactDetails(convertContactToEntity(customerDetails.getContactDetails()))
                .addressDetails(convertAddressToEntity(customerDetails.getAddressDetails()))
                .build();
    }

    private Address convertAddressToEntity(AddressDetails addressDetails) {
        return Address.builder()
                .addressLine1(addressDetails.getAddressLine1())
                .addressLine2(addressDetails.getAddressLine2())
                .city(addressDetails.getCity())
                .county(addressDetails.getCounty())
                .country(addressDetails.getCountry())
                .postcode(addressDetails.getPostcode())
                .build();
    }

    private Contact convertContactToEntity(ContactDetails contactDetails) {
        return Contact.builder()
                .email(contactDetails.getEmail())
                .mobile(contactDetails.getMobile())
                .build();
    }

    public CustomerDetails convertToCustomerPojo(Customer customer) {
        return CustomerDetails.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .createDateTime(customer.getCreateDateTime())
                .addressDetails(convertAddressToPojo(customer.getAddressDetails()))
                .contactDetails(convertContactToPojo(customer.getContactDetails()))
                .customerNumber(customer.getCustomerNumber())
                .build();
    }

    private ContactDetails convertContactToPojo(Contact contactDetails) {
        return ContactDetails.builder()
                .email(contactDetails.getEmail())
                .mobile(contactDetails.getMobile())
                .build();
    }

    private AddressDetails convertAddressToPojo(Address address) {
        return AddressDetails.builder()
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .county(address.getCounty())
                .postcode(address.getPostcode())
                .country(address.getCountry())
                .build();
    }

    public AccountDetails convertToAccountPojo(Account account) {
        return AccountDetails.builder()
                .accountNumber(account.getAccountNumber())
                .customerNumber(account.getCustomerNumber())
                .accountCreatedTime(account.getAccountCreatedTime())
                .bankInformationDetails(convertToBankInfoPojo(account.getBankInformation()))
                .build();
    }

    private BankInformationDetails convertToBankInfoPojo(BankInformation bankInformation) {
        return BankInformationDetails.builder()
                .branchName(bankInformation.getBranchName())
                .branchAddress(convertAddressToPojo(bankInformation.getBranchAddress()))
                .branchCode(bankInformation.getBranchCode())
                .build();
    }

    public Account convertAccountToEntity(AccountDetails accountDetails) {
        return Account.builder()
                .bankInformation(convertBankInfoToEntity(accountDetails.getBankInformationDetails()))
                .customerNumber(accountDetails.getCustomerNumber())
                .build();
    }

    private BankInformation convertBankInfoToEntity(BankInformationDetails bankInformationDetails) {
        return BankInformation.builder()
                .branchName(bankInformationDetails.getBranchName())
                .branchAddress(convertAddressToEntity(bankInformationDetails.getBranchAddress()))
                .branchCode(bankInformationDetails.getBranchCode())
                .build();
    }

//    public List<Transaction> convertToTransactionEntities(TransactionDetails transactionDetails) {
//        List<Transaction> transactions = new ArrayList<>();
//        Transaction primaryTransaction = getPrimaryTransaction(transactionDetails);
//        transactions.add(primaryTransaction);
//        if(primaryTransaction.getType()==TransferType.TRANSFER) {
//            Transaction secondaryTransaction = getSecondaryTransaction(transactionDetails);
//            transactions.add(secondaryTransaction);
//        }
//        return transactions;
//    }

    public Transaction convertToTransactionEntity(TransactionDetails transactionDetails) {
        return Transaction.builder()
        .amount(transactionDetails.getAmount())
        .accountNumber(UUID.fromString(transactionDetails.getAccountNumber()))
        .build();
    }

//    private Transaction getSecondaryTransaction(TransactionDetails transactionDetails) {
//        return Transaction.builder()
//                .amount(transactionDetails.getAmount() * -1)
//                .accountNumber(UUID.fromString(transactionDetails.getTransferAccountNumber()))
//                .transferAccountNumber(UUID.fromString(transactionDetails.getAccountNumber()))
//                .type(TransferType.TRANSFER)
//                .build();
//    }

//    private Transaction getPrimaryTransaction(TransactionDetails transactionDetails) {
//        return Transaction.builder()
//                .amount(transactionDetails.getAmount())
//                .accountNumber(UUID.fromString(transactionDetails.getAccountNumber()))
//                .transferAccountNumber(transactionDetails.getTransferAccountNumber() != null ? UUID.fromString(transactionDetails.getTransferAccountNumber()) : null)
//                .type(getTransferType(transactionDetails.getTransferAccountNumber(), transactionDetails.getAmount()))
//                .build();
//    }

//    private TransferType getTransferType(String accountNumber, Double amount) {
//        if(accountNumber==null){
//            return amount >= 0 ? TransferType.DEPOSIT : TransferType.WITHDRAW;
//        }
//        return TransferType.TRANSFER;
//    }

    public TransactionDetails convertToTransactionPojo(Transaction transaction) {
        return TransactionDetails.builder()
                .accountNumber(transaction.getAccountNumber().toString())
                .amount(transaction.getAmount())
                .transactionID(transaction.getId().toString())
                .build();
    }
}
