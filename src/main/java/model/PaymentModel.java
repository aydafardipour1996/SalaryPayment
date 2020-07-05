package model;

import java.math.BigDecimal;

public class PaymentModel {
    private boolean isDebtor;
    private String depositNumber;
    private BigDecimal amount;

    public PaymentModel(boolean isDebtor, String depositNumber, BigDecimal amount) {
        this.isDebtor = isDebtor;
        this.depositNumber = depositNumber;
        this.amount = amount;
    }

    public void setDebtor(boolean debtor) {
        isDebtor = debtor;
    }

    public boolean isDebtorSet() {
        return isDebtor;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
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
        String paymentString;

        if (isDebtor) {

            paymentString = "debtor" + tab + depositNumber + tab + amount;

        } else {

            paymentString = "creditor" + tab + depositNumber + tab + amount;

        }

        return paymentString;
    }
}
