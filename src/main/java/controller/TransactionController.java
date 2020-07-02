package controller;

import view.TransactionView;
import model.TransactionModel;
import sevices.WriteToFileService;

import java.math.BigDecimal;

public class TransactionController {
    String tab = "\t";
    private TransactionModel transactionModel;
    private TransactionView transactionView;
    private WriteToFileService service;

    public TransactionController() {

    }

    public TransactionController(TransactionModel transactionModel, TransactionView transactionView, WriteToFileService service) {
        this.transactionModel = transactionModel;
        this.transactionView = transactionView;
        this.service = service;
    }

    public void updateTransaction() {
        WriteToFileService.writeTransaction();
    }


    public String getTransactionDebtorDepositNumber() {
        return transactionModel.getDebtorDepositNumber();
    }

    public void setTransactionDebtorDepositNumber(String debtorDepositNumber) {
        transactionModel.setDebtorDepositNumber(debtorDepositNumber);
    }

    public String getTransactionCreditorDepositNumber() {
        return transactionModel.getCreditorDepositNumber();
    }

    public void setTransactionCreditorDepositNumber(String CreditorDepositNumber) {
        transactionModel.setCreditorDepositNumber(CreditorDepositNumber);
    }

    public BigDecimal getTransactionAmount() {

        return transactionModel.getAmount();
    }

    public void setTransactionAmount(BigDecimal amount) {
        transactionModel.setAmount(amount);
    }

    public void updateTransactionViewLine() {
        transactionView.writeLine(transactionModel.getDebtorDepositNumber(), transactionModel.getCreditorDepositNumber(), transactionModel.getAmount());
    }

    public void addDataToView() {
        transactionView.add(transactionModel.getDebtorDepositNumber(), transactionModel.getCreditorDepositNumber(), transactionModel.getAmount());
        service.addTransaction(transactionModel.getDebtorDepositNumber(), transactionModel.getCreditorDepositNumber(), transactionModel.getAmount());
    }
}
