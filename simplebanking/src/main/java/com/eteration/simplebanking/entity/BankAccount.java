package com.eteration.simplebanking.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "owner", nullable = false)
    private String owner;

    @Column(name = "balance", nullable = false)
    private double balance;

    @CreatedDate
    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @LastModifiedDate
    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }
}
