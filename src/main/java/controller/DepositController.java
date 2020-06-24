package controller;

import View.DepositView;
import model.DepositModel;

import java.math.BigDecimal;

public class DepositController {

    private final DepositModel depositModel;
    private final DepositView depositView;

    public DepositController(DepositModel depositModel, DepositView depositView) {
        this.depositModel = depositModel;
        this.depositView = depositView;
    }

    public static void updateDepositView() {
        DepositView.write();
    }

    public String getDepositDepositNumber() {
        return depositModel.getDepositNumber();
    }

    public void setDepositDepositNumber(String depositNumber) {
        depositModel.setDepositNumber(depositNumber);
    }

    public BigDecimal getDepositAmount() {
        return depositModel.getAmount();
    }

    public void setDepositAmount(BigDecimal amount) {
        depositModel.setAmount(amount);
    }

    public void addDataToView() {
        depositView.add(depositModel.getDepositNumber(), depositModel.getAmount());
    }
}


