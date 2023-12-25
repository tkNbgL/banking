package com.eteration.simplebanking.service.transaction.impl;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.PhoneGsmBilling;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.service.transaction.BillPaymentTransaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionPhoneBillPayment implements BillPaymentTransaction {

    @Override
    public Transaction postBillingTransaction(BankAccount bankAccount, String company, String gsm, double amount) {
        Transaction phoneBillingTransaction = new Transaction();
        phoneBillingTransaction.setAmount(amount);
        phoneBillingTransaction.setType(TransactionType.BILL_PAYMENT.name());
        phoneBillingTransaction.setApprovalCode(UUID.randomUUID().toString());
        phoneBillingTransaction.setApprovalCode(UUID.randomUUID().toString());

        PhoneGsmBilling phoneGsmBilling = new PhoneGsmBilling();
        phoneGsmBilling.setCompany(company);
        phoneGsmBilling.setGsm(gsm);
        phoneGsmBilling.setTransaction(phoneBillingTransaction);

        phoneBillingTransaction.setPhoneGsmBilling(phoneGsmBilling);

        double balance = bankAccount.getBalance();
        bankAccount.setBalance(balance - amount);

        phoneBillingTransaction.setBankAccount(bankAccount);
        return phoneBillingTransaction;
    }

    @Override
    public TransactionType getTransactionType() {
        return TransactionType.BILL_PAYMENT;
    }
}
