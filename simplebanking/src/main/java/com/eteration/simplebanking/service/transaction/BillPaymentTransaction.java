package com.eteration.simplebanking.service.transaction;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;

import java.math.BigDecimal;

public interface BillPaymentTransaction extends TypeAwereTransaction {
    Transaction postBillingTransaction(BankAccount bankAccount, String company, String gsm, double amount);
}
