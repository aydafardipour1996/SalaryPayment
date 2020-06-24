package model;

import java.math.BigDecimal;

public class DepositModel {
    private String DepositNumber;
    private BigDecimal amount;

    public DepositModel(String depositNumber, BigDecimal amount) {
        DepositNumber = depositNumber;
        this.amount = amount;
    }

    public String getDepositNumber() {
        return DepositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        DepositNumber = depositNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


}
