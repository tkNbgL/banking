package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.model.rest.AccountInfoResponseDto;
import com.eteration.simplebanking.model.rest.PhoneBillingTransactionRequestDto;
import com.eteration.simplebanking.model.rest.TransactionApplyRequestDto;
import com.eteration.simplebanking.model.rest.TransactionApplyResponseDto;
import com.eteration.simplebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/{api-version}")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/credit/{account-number}")
    public ResponseEntity<TransactionApplyResponseDto> deposit(@RequestBody TransactionApplyRequestDto requestDto,
                                                               @PathVariable(value = "account-number") String accountNumber) {
        return ResponseEntity.ok(accountService.applyTransaction(requestDto, TransactionType.DEPOSIT, accountNumber));
    }

    @PostMapping("/debit/{account-number}")
    public ResponseEntity<TransactionApplyResponseDto> withdraw(@RequestBody TransactionApplyRequestDto requestDto,
                                                                @PathVariable(value = "account-number") String accountNumber) {
        return ResponseEntity.ok(accountService.applyTransaction(requestDto, TransactionType.WITHDRAWAL, accountNumber));
    }

    @PostMapping("/debit/phone/{account-number}")
    public ResponseEntity<TransactionApplyResponseDto> payPhoneBill(@RequestBody PhoneBillingTransactionRequestDto requestDto,
                                                                @PathVariable(value = "account-number") String accountNumber) {
        return ResponseEntity.ok(accountService.applyBillingTransaction(requestDto, TransactionType.BILL_PAYMENT, accountNumber));
    }

    @GetMapping("/{account-number}")
    public ResponseEntity<AccountInfoResponseDto> accountInfo(@PathVariable(value = "account-number")String accountNumber) {
        return ResponseEntity.ok(accountService.retrieveAccountInfo(accountNumber));
    }
}
