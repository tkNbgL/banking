package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
