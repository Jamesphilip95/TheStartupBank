package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.BaseTest;
import com.coding.exercise.bankapp.common.ResourceNotFoundException;
import com.coding.exercise.bankapp.pojos.TransactionDetails;
import com.coding.exercise.bankapp.service.TransactionService;
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
public class TransactionControllerTest {


    public static final String TRANSACTIONS_PATH = "/transactions";
    @MockBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void initialiseRestAssuredMockMvcStandalone() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void createTransactionSuccessShouldReturn201() {
        given()
                .body(BaseTest.buildTransactionDetailsPayload())
                .contentType(ContentType.JSON)
                .post(TRANSACTIONS_PATH)
                .then()
                    .statusCode(CREATED.value());
    }

    @Test
    public void createTransactionShouldReturn400ForMissingPayload() {
        given()
                .contentType(ContentType.JSON)
                .post(TRANSACTIONS_PATH)
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

    @Test
    public void listTransactionsHappyPathTest() {
        given()
                .contentType(ContentType.JSON)
                .get(TRANSACTIONS_PATH)
                .then()
                    .statusCode(OK.value());
    }

    @Test
    public void getTransactionHappyPathTest() {
        TransactionDetails accountDetails = BaseTest.buildTransactionDetailsPayload();
        when(transactionService.getTransaction("567e2712-cafe-4204-8449-2059435c24a0")).thenReturn(accountDetails);

        given()
                .contentType(ContentType.JSON)
                .get(TRANSACTIONS_PATH + "/567e2712-cafe-4204-8449-2059435c24a0")
                .then()
                    .statusCode(OK.value());
    }

    @Test
    public void notFoundTransactionTest() {
        when(transactionService.getTransaction("98765")).thenThrow(ResourceNotFoundException.class);
        given()
                .contentType(ContentType.JSON)
                .get(TRANSACTIONS_PATH + "/98765")
                .then()
                    .statusCode(NOT_FOUND.value());
    }

    @Test
    public void createTransactionShouldReturn400ForInvalidPayload() {
        TransactionDetails transactionDetails = BaseTest.buildTransactionDetailsPayload();
        transactionDetails.setAmount(null);
        given()
                .body(transactionDetails)
                .contentType(ContentType.JSON)
                .post(TRANSACTIONS_PATH)
                .then()
                    .statusCode(BAD_REQUEST.value());
    }

}
