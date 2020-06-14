package controller;

import View.BalanceView;
import model.BalanceModel;

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

    public void updateBalanceView(){

    }
}


