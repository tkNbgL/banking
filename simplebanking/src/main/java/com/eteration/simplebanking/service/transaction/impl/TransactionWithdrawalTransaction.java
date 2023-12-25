package com.eteration.simplebanking.service.transaction.impl;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.service.transaction.AccountTransaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionWithdrawalTransaction implements AccountTransaction {

    @Override
    public Transaction post(BankAccount bankAccount, double amount) {
        Transaction deposit = new Transaction();
        deposit.setAmount(amount);
        deposit.setType(TransactionType.WITHDRAWAL.name());
        deposit.setApprovalCode(UUID.randomUUID().toString());

        double balance = bankAccount.getBalance();
        bankAccount.setBalance(balance - amount);

        deposit.setBankAccount(bankAccount);
        return deposit;
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.WITHDRAWAL;
    }
}
