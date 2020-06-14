package model;

public class PaymentModel {
    private boolean isDebtor;
    private String depositNumber;
    private double amount;

    public void setDebtor(boolean debtor) {
        isDebtor = debtor;
    }

    public boolean isDebtor() {
        return isDebtor;
    }

    public void setDepositNumber(String depositNumber) {
        this.depositNumber = depositNumber;
    }

    public String getDepositNumber() {
        return depositNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }




}
