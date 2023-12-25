package com.eteration.simplebanking;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.service.transaction.impl.TransactionDeposit;
import com.eteration.simplebanking.service.transaction.impl.TransactionPhoneBillPayment;
import com.eteration.simplebanking.service.transaction.impl.TransactionWithdrawalTransaction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @InjectMocks
    private TransactionDeposit transactionDeposit;

    @InjectMocks
    private TransactionWithdrawalTransaction transactionWithdrawal;

    @InjectMocks
    private TransactionPhoneBillPayment transactionPhoneBillPayment;

    @Test
    public void it_should_add_balance_when_deposit() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionDeposit.post(bankAccount, Double.valueOf("10"));

        Assertions.assertEquals(bankAccount.getBalance(), Double.valueOf("20"));
    }

    @Test
    public void it_should_sub_balance_when_withdraw() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionWithdrawal.post(bankAccount, Double.valueOf("10"));

        Assertions.assertEquals(bankAccount.getBalance(), Double.valueOf("0"));
    }

    @Test
    public void it_should_sub_balance_when_bill_payment() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionPhoneBillPayment
                .postBillingTransaction(bankAccount, "Vodafone", "5423345566", Double.valueOf("10"));

        Assertions.assertEquals(bankAccount.getBalance(), Double.valueOf("0"));
    }

    @Test
    public void it_should_calculate_corrent_balance_with_all_transactions() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner("jim");
        bankAccount.setBalance(Double.valueOf("12345"));

        transactionDeposit.post(bankAccount, Double.valueOf("1000"));
        transactionWithdrawal.post(bankAccount, Double.valueOf("200"));
        transactionPhoneBillPayment
                .postBillingTransaction(bankAccount, "Vodafone", "5423345566", Double.valueOf("96.50"));

        Assertions.assertEquals(bankAccount.getBalance(), Double.valueOf("13048.5"));
    }

    @Test
    public void it_should_return_deposit_transaction() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionDeposit.post(bankAccount, Double.valueOf("10"));

        Assertions.assertEquals(post.getType(), TransactionType.DEPOSIT.name());
    }

    @Test
    public void it_should_return_withdraw_transaction() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionWithdrawal.post(bankAccount, Double.valueOf("10"));

        Assertions.assertEquals(post.getType(), TransactionType.WITHDRAWAL.name());
    }

    @Test
    public void it_should_return_bill_payment_transaction() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionPhoneBillPayment
                .postBillingTransaction(bankAccount, "Vodafone", "5423345566", Double.valueOf("96.50"));

        Assertions.assertEquals(post.getType(), TransactionType.BILL_PAYMENT.name());
    }

    @Test
    public void it_should_generate_approval_code() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));

        Transaction post = transactionWithdrawal.post(bankAccount, Double.valueOf("10"));

        Assertions.assertNotNull(post.getApprovalCode());
    }
}
