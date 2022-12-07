package com.coding.exercise.bankapp.service.helper;

import com.coding.exercise.bankapp.model.Address;
import com.coding.exercise.bankapp.model.Contact;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.pojos.AddressDetails;
import com.coding.exercise.bankapp.pojos.ContactDetails;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import org.springframework.stereotype.Component;

import static com.coding.exercise.bankapp.TheStartupBankApplication.createID;

@Component
public class BankServiceHelper {
    public Customer convertCustomerToEntity(CustomerDetails customerDetails) {
        return Customer.builder()
                .firstName(customerDetails.getFirstName())
                .lastName(customerDetails.getLastName())
                .customerNumber(createID())
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
}
