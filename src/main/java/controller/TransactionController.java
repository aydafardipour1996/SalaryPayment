package controller;

import View.TransactionView;
import model.TransactionModel;

public class TransactionController {
    private TransactionModel transactionModel;
    private TransactionView transactionView;

    public TransactionController(TransactionModel transactionModel, TransactionView transactionView) {
        this.transactionModel = transactionModel;
        this.transactionView = transactionView;
    }

    public void setTransactionSender(String sender) {
        transactionModel.setSender(sender);
    }

    public String getTransactionSender(){
        return transactionModel.getSender();
    }

    public void setTransactionReceiver(String receiver) {
      transactionModel.setReceiver(receiver);
    }

    public String getTransactionReceiver(){
        return transactionModel.getReceiver();
    }

    public void setTransactionAmount(double amount) {
        transactionModel.setAmount(amount);
    }

    public double getTransactionAmount(){

        return transactionModel.getAmount();
    }

    public void updateTransactionView(){

    }
}
