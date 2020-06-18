package controller;

import View.BalanceView;
import View.TransactionView;
import model.BalanceModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BalanceController {

    private BalanceModel balanceModel;
    private BalanceView balanceView;
    public BalanceController(BalanceModel balanceModel, BalanceView balanceView) {
        this.balanceModel = balanceModel;
        this.balanceView = balanceView;
    }

    public void setBalanceDepositNumber(String depositNumber){
        balanceModel.setDepositNumber(depositNumber);
    }

    public String getBalanceDepositNumber(){
        return balanceModel.getDepositNumber();
    }

    public void setBalanceAmount(double balance){
        balanceModel.setBalance(balance);
    }

    public double getBalanceAmount(){
        return balanceModel.getBalance();
    }


    public void addDataToView(){
        balanceView.add(balanceModel.getDepositNumber(),balanceModel.getBalance());
    }
    public static void updateBalanceView(){
        BalanceView.write();
    }
}


