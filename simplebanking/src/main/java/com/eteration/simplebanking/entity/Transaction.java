package com.eteration.simplebanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "transaction_id")
    private String transactionId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_number")
    private BankAccount bankAccount;

    @CreatedDate
    @Column(name = "date", nullable = false)
    private Instant date;
    private double amount;
    private String type;
    @Column(name = "approval_code")
    private String approvalCode;

    @JsonIgnore
    @OneToOne(mappedBy = "transaction")
    private PhoneGsmBilling phoneGsmBilling;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApprovalCode() {
        return approvalCode;
    }

    public void setApprovalCode(String approvalCode) {
        this.approvalCode = approvalCode;
    }

    public PhoneGsmBilling getPhoneGsmBilling() {
        return phoneGsmBilling;
    }

    public void setPhoneGsmBilling(PhoneGsmBilling phoneGsmBilling) {
        this.phoneGsmBilling = phoneGsmBilling;
    }
}
