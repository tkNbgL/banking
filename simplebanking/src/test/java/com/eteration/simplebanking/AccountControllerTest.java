package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.model.rest.AccountInfoResponseDto;
import com.eteration.simplebanking.model.rest.TransactionApplyRequestDto;
import com.eteration.simplebanking.model.rest.TransactionApplyResponseDto;
import com.eteration.simplebanking.service.AccountService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("local")
@ContextConfiguration
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Spy
    @InjectMocks
    private AccountController accountController;

    @MockBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void it_should_return_account_info_response() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAmount(Double.valueOf("10"));
        transaction.setType("DEPOSIT");

        AccountInfoResponseDto responseDto = new AccountInfoResponseDto();
        responseDto.setBalance(Double.valueOf("10"));
        responseDto.setAccountNumber("112233");
        responseDto.setOwner("Kim");
        responseDto.getTransactions().add(transaction);

        Mockito.when(accountService.retrieveAccountInfo(anyString()))
                .thenReturn(responseDto);

        mockMvc.perform(
                get("/account/v1/112233")
                    .content(MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"accountNumber\":\"112233\",\"owner\":\"Kim\",\"balance\":10.0,\"createDate\":null,\"transactions\":[{\"transactionId\":null,\"date\":null,\"amount\":10.0,\"type\":\"DEPOSIT\",\"approvalCode\":null}]}"));

    }

    @Test
    public void it_should_return_transaction_statis_and_approval_code() throws Exception {
        TransactionApplyRequestDto requestDto = new TransactionApplyRequestDto();
        requestDto.setAmount("10");

        TransactionApplyResponseDto responseDto = new TransactionApplyResponseDto();
        responseDto.setStatus("OK");
        responseDto.setApprovalCode("1122334455");

        Mockito.when(accountService.applyTransaction(requestDto, TransactionType.WITHDRAWAL, "112233"))
                .thenReturn(responseDto);

        ResponseEntity<TransactionApplyResponseDto> withdraw = accountController.withdraw(requestDto, "112233");

        Assertions.assertEquals(withdraw.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(withdraw.getBody().getStatus(), "OK");
        Assertions.assertEquals(withdraw.getBody().getApprovalCode(), "1122334455");

    }
}
