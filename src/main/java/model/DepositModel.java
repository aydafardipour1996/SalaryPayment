package model;

import java.math.BigDecimal;

public class DepositModel {
    private String depositNumber;
    private BigDecimal deposit;
    private long position;

    public DepositModel(String depositNumber, BigDecimal deposit, long position) {
        this.depositNumber = depositNumber;
        this.deposit = deposit;
        this.position = position;
    }


    public String getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    @Override
    public String toString() {

        String tab = "\t";

        return depositNumber + tab + deposit;

    }

    public String positionString() {

        String tab = "\t";

        return depositNumber + tab + position;

    }

}
