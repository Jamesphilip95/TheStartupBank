package com.coding.exercise.bankapp.service.helper;

import com.coding.exercise.bankapp.model.*;
import com.coding.exercise.bankapp.pojos.*;
import org.springframework.stereotype.Component;
@Component
public class BankServiceHelper {
    public Customer convertCustomerToEntity(CustomerDetails customerDetails) {
        return Customer.builder()
                .firstName(customerDetails.getFirstName())
                .lastName(customerDetails.getLastName())
                .accounts(customerDetails.getAccounts())
                .customerNumber(customerDetails.getCustomerNumber())
                .createDateTime(customerDetails.getCreateDateTime())
                .contactDetails(convertContactToEntity(customerDetails.getContactDetails()))
                .address(convertAddressToEntity(customerDetails.getAddressDetails()))
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
                .accounts(customer.getAccounts())
                .createDateTime(customer.getCreateDateTime())
                .addressDetails(convertAddressToPojo(customer.getAddress()))
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
                .accountBalance(account.getAccountBalance())
                .transactions(account.getTransactions())
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

    public Transaction createTransactionEntity(Long customerNumber, TransactionDetails transactionDetails, TransferType transferType) {
        return Transaction.builder()
                .amount(transactionDetails.getAmount())
                .accountNumber(transactionDetails.getAccountNumber())
                .transferAccountNumber(transactionDetails.getTransferAccountNumber())
                .type(transferType)
                .customerNumber(customerNumber)
                .build();
    }

    public Transaction convertToDepositEntity(Long customerNumber, TransactionDetails transactionDetails) {
        return createTransactionEntity(customerNumber, transactionDetails, TransferType.DEPOSIT);
    }

    public Transaction convertToWithdrawEntity(Long customerNumber, TransactionDetails transactionDetails) {
        return createTransactionEntity(customerNumber, transactionDetails, TransferType.WITHDRAW);
    }

    public TransactionDetails convertToTransactionPojo(Transaction transaction) {
        return TransactionDetails.builder()
                .accountNumber(transaction.getAccountNumber())
                .customerNumber(transaction.getCustomerNumber())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .transferAccountNumber(transaction.getTransferAccountNumber())
                .build();
    }
}
