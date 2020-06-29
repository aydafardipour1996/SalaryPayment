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


    public void setDebtorDepositNumber(String debtorDepositNumber) {
        this.debtorDepositNumber = debtorDepositNumber;
    }

    public String getDebtorDepositNumber() {
        return debtorDepositNumber;
    }

    public void setCreditorDepositNumber(String creditorDepositNumber) {
        this.creditorDepositNumber = creditorDepositNumber;
    }

    public String getCreditorDepositNumber() {
        return creditorDepositNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
