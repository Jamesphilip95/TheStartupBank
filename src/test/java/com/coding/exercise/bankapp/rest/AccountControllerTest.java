package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.pojos.AccountDetails;
import com.coding.exercise.bankapp.service.AccountService;
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
public class AccountControllerTest {


    public static final String ACCOUNTS_PATH = "/accounts";
    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void createAccountSuccessShouldReturn201() {
        given()
                .body(BaseTest.buildAccountDetailsPayload())
                .contentType(ContentType.JSON)
                .post(ACCOUNTS_PATH)
                .then()
                    .statusCode(CREATED.value());
    }

    @Test
    public void createAccountShouldReturn400ForMissingPayload() {
        given()
                .contentType(ContentType.JSON)
                .post(ACCOUNTS_PATH)
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void listAccountsHappyPathTest() {
        given()
                .contentType(ContentType.JSON)
                .get(ACCOUNTS_PATH)
                .then()
                    .statusCode(OK.value());
    }

    @Test
    public void getAccountHappyPathTest() {
        when(accountService.getAccount("567e2712-cafe-4204-8449-2059435c24a0")).thenReturn(BaseTest.buildAccountDetailsPayload());
        given()
                .contentType(ContentType.JSON)
                .get(ACCOUNTS_PATH + "/567e2712-cafe-4204-8449-2059435c24a0")
                .then()
                    .statusCode(OK.value());
    }

    @Test
    public void notFoundCustomerTest() {
        when(accountService.getAccount("98765")).thenThrow(ResourceNotFoundException.class);
        given()
                .contentType(ContentType.JSON)
                .get(ACCOUNTS_PATH + "/98765")
                .then()
                    .statusCode(NOT_FOUND.value());
    }

    @Test
    public void createAccountShouldReturn400ForInvalidPayload() {
        AccountDetails accountDetails = BaseTest.buildAccountDetailsPayload();
        accountDetails.setCustomerNumber(null);
        given()
                .body(accountDetails)
                .contentType(ContentType.JSON)
                .post(ACCOUNTS_PATH)
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

}
