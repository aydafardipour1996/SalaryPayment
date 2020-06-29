package controller;

import View.DepositView;
import model.DepositModel;
import sevices.WriteToFileService;

import java.math.BigDecimal;

public class DepositController {

    private DepositModel depositModel;
    private DepositView depositView;
    private WriteToFileService service;

    public DepositController() {
    }

    public DepositController(DepositModel depositModel, DepositView depositView, WriteToFileService service) {
        this.depositModel = depositModel;
        this.depositView = depositView;
        this.service = service;
    }

    public void updateDepositView() {
        WriteToFileService.writeDeposit();
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
        service.addDeposit(depositModel.getDepositNumber(), depositModel.getAmount());
    }
}


