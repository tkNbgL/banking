package com.eteration.simplebanking.service;

import com.eteration.simplebanking.entity.BankAccount;
import com.eteration.simplebanking.entity.Transaction;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.TransactionTypeNotValidException;
import com.eteration.simplebanking.model.TransactionType;
import com.eteration.simplebanking.model.rest.AccountInfoResponseDto;
import com.eteration.simplebanking.model.rest.PhoneBillingTransactionRequestDto;
import com.eteration.simplebanking.model.rest.TransactionApplyRequestDto;
import com.eteration.simplebanking.model.rest.TransactionApplyResponseDto;
import com.eteration.simplebanking.repository.BankAccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import com.eteration.simplebanking.service.transaction.AccountTransaction;
import com.eteration.simplebanking.service.transaction.impl.TransactionPhoneBillPayment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final List<AccountTransaction> typeAwareTransactions;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionPhoneBillPayment billPaymentTransaction;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(List<AccountTransaction> typeAwareTransactions, BankAccountRepository bankAccountRepository, TransactionPhoneBillPayment billPaymentTransaction, TransactionRepository transactionRepository) {
        this.typeAwareTransactions = typeAwareTransactions;
        this.bankAccountRepository = bankAccountRepository;
        this.billPaymentTransaction = billPaymentTransaction;
        this.transactionRepository = transactionRepository;
    }

    public TransactionApplyResponseDto applyTransaction(TransactionApplyRequestDto requestDto,
                                                        TransactionType transactionType,
                                                        String accountNumber) {
        //find account by Id
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findByAccountNumber(accountNumber);
        if (!bankAccountOpt.isPresent()) {
            throw new AccountNotFoundException("account not found");
        }

        AccountTransaction accountTransaction = forTransaction(transactionType);
        Transaction post = accountTransaction.post(bankAccountOpt.get(), Double.valueOf(requestDto.getAmount()));

        bankAccountOpt.get().getTransactions().add(post);
        bankAccountRepository.save(bankAccountOpt.get());

        return TransactionApplyResponseDto.of("OK", post.getApprovalCode());
    }

    public TransactionApplyResponseDto applyBillingTransaction(PhoneBillingTransactionRequestDto requestDto,
                                                               TransactionType transactionType,
                                                               String accountNumber) {
        //find account by Id
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findByAccountNumber(accountNumber);
        if (!bankAccountOpt.isPresent()) {
            throw new AccountNotFoundException("account not found");
        }

        Transaction transaction = billPaymentTransaction
                .postBillingTransaction(bankAccountOpt.get(), requestDto.getCompany(),
                        requestDto.getGsm(), Double.valueOf(requestDto.getAmount()));

        bankAccountOpt.get().getTransactions().add(transaction);

        return TransactionApplyResponseDto.of("OK", transaction.getApprovalCode());
    }

    public AccountInfoResponseDto retrieveAccountInfo(String accountNumber) {
        Optional<BankAccount> bankAccountOpt = bankAccountRepository.findByAccountNumber(accountNumber);
        if (!bankAccountOpt.isPresent()) {
            throw new AccountNotFoundException("account not found");
        }

        AccountInfoResponseDto accountInfoResponseDto = new AccountInfoResponseDto();
        BeanUtils.copyProperties(bankAccountOpt.get(), accountInfoResponseDto);

        return accountInfoResponseDto;
    }

    private AccountTransaction forTransaction(TransactionType transactionType) {
        return this.typeAwareTransactions
                .stream()
                .filter(transaction -> transactionType.equals(transaction.getTransactionType()))
                .findFirst()
                .orElseThrow(() -> new TransactionTypeNotValidException("not a valid transaction"));
    }
}
