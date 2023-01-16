package com.coding.exercise.bankapp.rest;

import com.coding.exercise.bankapp.BaseTest;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
    public void createAccountSuccessShouldReturn200() {
        given().body(BaseTest.buildAccountDetailsPayload())
                .contentType(ContentType.JSON)
                .post(ACCOUNTS_PATH)
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void registerShouldReturn400ForMissingPayload() {
        given().contentType(ContentType.JSON)
                .post(ACCOUNTS_PATH)
                .then()
                .assertThat().statusCode(400);
    }

    @Test
    public void listCustomersHappyPathTest() {
        List<AccountDetails> allAccountDetails = new ArrayList<>();
        allAccountDetails.add(BaseTest.buildAccountDetailsPayload());
        when(accountService.findAccounts(any())).thenReturn(ResponseEntity.ok().body(allAccountDetails));

        given().contentType(ContentType.JSON)
                .get(ACCOUNTS_PATH)
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void getCustomerHappyPathTest() {

        AccountDetails accountDetails = BaseTest.buildAccountDetailsPayload();
        when(accountService.getAccount("567e2712-cafe-4204-8449-2059435c24a0")).thenReturn(ResponseEntity.ok().body(accountDetails));

        given().contentType(ContentType.JSON)
                .get(ACCOUNTS_PATH + "/567e2712-cafe-4204-8449-2059435c24a0")
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void notFoundCustomerTest() {
        when(accountService.getAccount(any())).thenReturn(ResponseEntity.notFound().build());
        given().contentType(ContentType.JSON)
                .get(ACCOUNTS_PATH + "/98765")
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void registerShouldReturn400ForInvalidPayload() {
        AccountDetails accountDetails = BaseTest.buildAccountDetailsPayload();
        accountDetails.setCustomerNumber(null);
        given().body(accountDetails)
                .contentType(ContentType.JSON)
                .post(ACCOUNTS_PATH)
                .then()
                .assertThat().statusCode(400);
    }

}
