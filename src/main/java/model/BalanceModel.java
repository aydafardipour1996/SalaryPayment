package model;

public class BalanceModel {
    private String DepositNumber;
    private double balance;

    public void setDepositNumber(String depositNumber) {
        DepositNumber = depositNumber;
    }

    public String getDepositNumber() {
        return DepositNumber;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }



}
