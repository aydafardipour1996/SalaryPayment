package model;

import java.math.BigDecimal;

public class DepositModel {
    private String depositNumber;
    private BigDecimal amount;

    public DepositModel(String depositNumber, BigDecimal amount) {
        this.depositNumber = depositNumber;
        this.amount = amount;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        depositNumber = depositNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        String tab = "\t";
        return depositNumber + tab + amount;
    }

}
