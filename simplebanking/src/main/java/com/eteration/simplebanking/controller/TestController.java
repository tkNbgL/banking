package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/saveBankAccount")
    public String saveOwner() {
        System.out.println("Owner save called...");

        BankAccount account = new BankAccount();
        account.setBalance(190);
        account.setOwner("utku");

        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setAmount(100);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        account.setTransactions(transactions);

        BankAccount bankAccount = bankAccountRepository.save(account);
        System.out.println("Owner out :: " + bankAccount);

        System.out.println("Saved!!!");
        return "Owner saved!!!";
    }

    @GetMapping("/readAllBankAccounts")
    public List<BankAccount> read() {
        return bankAccountRepository.findAll();

    }
}
