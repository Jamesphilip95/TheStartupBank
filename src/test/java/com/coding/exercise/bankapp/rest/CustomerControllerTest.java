package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.pojos.CustomerDetails;
import com.coding.exercise.bankapp.service.CustomerService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {


    public static final String CUSTOMERS_PATH = "/customers";
    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void registerSuccessShouldReturn201() {
        given().body(BaseTest.buildCustomerDetailsPayload())
                .contentType(ContentType.JSON)
                .post(CUSTOMERS_PATH)
                .then()
                .assertThat().statusCode(201);
    }

    @Test
    public void listCustomersHappyPathTest() {
        List<CustomerDetails> allCustomerDetails = new ArrayList<>();
        allCustomerDetails.add(BaseTest.buildCustomerDetailsPayload());
        when(customerService.findAllCustomers()).thenReturn(allCustomerDetails);

        given().contentType(ContentType.JSON)
                .get(CUSTOMERS_PATH)
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void getCustomerHappyPathTest() {

        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        when(customerService.getCustomer(customerDetails.getCustomerNumber())).thenReturn(customerDetails);

        given().contentType(ContentType.JSON)
                .get(CUSTOMERS_PATH + "/12345")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void notFoundCustomerTest() {
        when(customerService.getCustomer(98765L)).thenThrow(ResourceNotFoundException.class);
        given().contentType(ContentType.JSON)
                .get(CUSTOMERS_PATH + "/98765")
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void registerShouldReturn400ForMissingPayload() {
        given().contentType(ContentType.JSON)
                .post(CUSTOMERS_PATH)
                .then()
                .assertThat().statusCode(400);
    }

    @Test
    public void registerShouldReturn400ForInvalidPayload() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        customerDetails.setLastName(null);
        given().body(customerDetails)
                .contentType(ContentType.JSON)
                .post(CUSTOMERS_PATH)
                .then()
                .assertThat().statusCode(400);
    }

}
