package model;

import java.math.BigDecimal;

public class TransactionModel {
    private String debtorDepositNumber;
    private String creditorDepositNumber;
    private BigDecimal amount;

    public TransactionModel(String debtorDepositNumber, String creditorDepositNumber, BigDecimal amount) {
        this.debtorDepositNumber = debtorDepositNumber;
        this.creditorDepositNumber = creditorDepositNumber;
        this.amount = amount;
    }

    public String getSender() {
        return debtorDepositNumber;
    }

    public void setSender(String sender) {
        this.debtorDepositNumber = sender;
    }

    public String getReceiver() {
        return creditorDepositNumber;
    }

    public void setReceiver(String receiver) {
        this.creditorDepositNumber = receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
