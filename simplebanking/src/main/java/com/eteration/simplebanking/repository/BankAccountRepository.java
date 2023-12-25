package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
