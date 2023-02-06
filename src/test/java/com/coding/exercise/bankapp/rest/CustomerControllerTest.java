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

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;

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
        given()
                .body(BaseTest.buildCustomerDetailsPayload())
                .contentType(ContentType.JSON)
                .post(CUSTOMERS_PATH)
                .then()
                    .statusCode(CREATED.value());
    }

    @Test
    public void listCustomersHappyPathTest() {
        given()
                .contentType(ContentType.JSON)
                .get(CUSTOMERS_PATH)
                .then()
                    .statusCode(OK.value());
    }

    @Test
    public void getCustomerHappyPathTest() {
        when(customerService.getCustomer(12345L)).thenReturn(BaseTest.buildCustomerDetailsPayload());
        given()
                .contentType(ContentType.JSON)
                .get(CUSTOMERS_PATH + "/12345")
                .then()
                    .statusCode(OK.value());
    }

    @Test
    public void notFoundCustomerTest() {
        when(customerService.getCustomer(98765L)).thenThrow(ResourceNotFoundException.class);
        given()
                .contentType(ContentType.JSON)
                .get(CUSTOMERS_PATH + "/98765")
                .then()
                    .statusCode(NOT_FOUND.value());
    }

    @Test
    public void registerShouldReturn400ForMissingPayload() {
        given()
                .contentType(ContentType.JSON)
                .post(CUSTOMERS_PATH)
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void registerShouldReturn400ForInvalidPayload() {
        CustomerDetails customerDetails = BaseTest.buildCustomerDetailsPayload();
        customerDetails.setLastName(null);
        given()
                .body(customerDetails)
                .contentType(ContentType.JSON)
                .post(CUSTOMERS_PATH)
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

}
