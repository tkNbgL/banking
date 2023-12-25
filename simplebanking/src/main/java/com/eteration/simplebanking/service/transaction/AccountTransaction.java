package com.eteration.simplebanking.service.transaction;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;

public interface AccountTransaction extends TypeAwereTransaction {
    Transaction post(BankAccount bankAccount, double amount);
}
