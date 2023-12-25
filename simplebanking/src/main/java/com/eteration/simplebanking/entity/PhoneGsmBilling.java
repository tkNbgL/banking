package com.eteration.simplebanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "phone_billing")
public class PhoneGsmBilling {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "bill_id")
    private String billId;

    private String company;

    private String gsm;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private Transaction transaction;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}