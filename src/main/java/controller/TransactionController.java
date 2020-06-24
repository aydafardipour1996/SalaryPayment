package controller;

import View.TransactionView;
import model.TransactionModel;

import java.math.BigDecimal;

public class TransactionController {
    private TransactionModel transactionModel;
    private TransactionView transactionView;

    public TransactionController(TransactionModel transactionModel, TransactionView transactionView) {
        this.transactionModel = transactionModel;
        this.transactionView = transactionView;
    }

    public static void updateTransaction() {
        TransactionView.write();
    }

    public String getTransactionSender() {
        return transactionModel.getSender();
    }

    public void setTransactionSender(String sender) {
        transactionModel.setSender(sender);
    }

    public String getTransactionReceiver() {
        return transactionModel.getReceiver();
    }

    public void setTransactionReceiver(String receiver) {
        transactionModel.setReceiver(receiver);
    }

    public BigDecimal getTransactionAmount() {

        return transactionModel.getAmount();
    }

    public void setTransactionAmount(BigDecimal amount) {
        transactionModel.setAmount(amount);
    }

    public void updateTransactionViewLine() {
        transactionView.writeLine(transactionModel.getSender(), transactionModel.getReceiver(), transactionModel.getAmount());
    }

    public void addDataToView() {
        transactionView.add(transactionModel.getSender(), transactionModel.getReceiver(), transactionModel.getAmount());
    }
}
