package controller;

import View.PaymentView;
import model.PaymentModel;

public class PaymentController {
    private PaymentModel paymentModel;
    private PaymentView paymentView;

    public PaymentController(PaymentModel paymentModel, PaymentView paymentView) {
        this.paymentModel = paymentModel;
        this.paymentView = paymentView;
    }

    public void setPaymentDebtor(boolean isDebtor) {
       paymentModel.setDebtor(isDebtor);
    }
    public boolean isPaymentDebtor(){
        return paymentModel.isDebtor();
    }

    public void setPaymentDepositNumber(String depositNumber){
        paymentModel.setDepositNumber(depositNumber);
    }

    public String getPaymentDepositNumber(){
        return paymentModel.getDepositNumber();
    }

    public void setPaymentAmount(double amount){
        paymentModel.setAmount(amount);
    }

    public double getPaymentAmount(){
        return paymentModel.getAmount();
    }
    public void updatePaymentView(){
    paymentView.write(paymentModel.isDebtor(),paymentModel.getDepositNumber(),paymentModel.getAmount());
    }
}
