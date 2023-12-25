package com.eteration.simplebanking;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.model.rest.TransactionApplyRequestDto;
import com.eteration.simplebanking.model.rest.TransactionApplyResponseDto;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.service.AccountService;
import com.eteration.simplebanking.service.transaction.AccountTransaction;
import com.eteration.simplebanking.service.transaction.impl.TransactionDeposit;
import com.eteration.simplebanking.service.transaction.impl.TransactionPhoneBillPayment;
import com.eteration.simplebanking.service.transaction.impl.TransactionWithdrawalTransaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Spy
    private List<AccountTransaction> accountTransactions = new ArrayList<>();

    @Mock
    private AccountTransaction accountTransaction;

    @Mock
    private TransactionDeposit transactionDeposit;

    @Mock
    private TransactionWithdrawalTransaction transactionWithdrawal;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private AccountService accountService;

    @Before
    public void init() {
        accountTransactions.add(transactionDeposit);
        accountTransactions.add(transactionWithdrawal);
    }

    @Test
    public void it_should_add_transaction_to_bank_account() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(Double.valueOf("10"));
        bankAccount.setOwner("kim");
        bankAccount.setAccountNumber("111222333");

        TransactionApplyRequestDto requestDto = new TransactionApplyRequestDto();
        requestDto.setAmount("10");

        Transaction transaction = new Transaction();
        transaction.setBankAccount(bankAccount);
        transaction.setType("DEPOSIT");
        transaction.setApprovalCode("123123");

        Mockito.when(bankAccountRepository.findByAccountNumber(bankAccount.getAccountNumber()))
                .thenReturn(Optional.of(bankAccount));

        Mockito.when(transactionDeposit.post(bankAccount, Double.valueOf(requestDto.getAmount())))
                .thenReturn(transaction);

        Mockito.when(transactionDeposit.getTransactionType())
                .thenReturn(TransactionType.DEPOSIT);

        TransactionApplyResponseDto responseDto = accountService
                .applyTransaction(requestDto, TransactionType.DEPOSIT, bankAccount.getAccountNumber());

        Assertions.assertNotNull(bankAccount.getTransactions());
    }
}
