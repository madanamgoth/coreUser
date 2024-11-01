package com.example.CoreUser.DTO;

public class PaymentRequestDTO {
    private String sourceAccount;
    private String destinationAccount;
    private double amount;
    private String currency;
    private String reference;
    private String pin;
    private String transactionId;

    public PaymentRequestDTO() {
    }

    public PaymentRequestDTO(String sourceAccount, String destinationAccount, double amount, String currency, String reference, String pin, String transactionId) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.currency = currency;
        this.reference = reference;
        this.pin = pin;
        this.transactionId = transactionId;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getTransactionId() {
        return transactionId;
    }
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
