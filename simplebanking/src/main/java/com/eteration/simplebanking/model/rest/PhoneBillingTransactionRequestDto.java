package com.eteration.simplebanking.model.rest;


public class PhoneBillingTransactionRequestDto {
    private String amount;
    private String company;
    private String gsm;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
}
