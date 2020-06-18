package controller;

import View.PaymentView;
import model.PaymentModel;

public class PaymentController {
    private final PaymentModel paymentModel;
    private final PaymentView paymentView;


    public PaymentController(PaymentModel paymentModel, PaymentView paymentView) {
        this.paymentModel = paymentModel;
        this.paymentView = paymentView;
    }

    public void setPaymentDebtor(boolean isDebtor) {
       paymentModel.setDebtor(isDebtor);
    }
    public boolean isPaymentDebtor(){
        return paymentModel.isDebtorSet();
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
    public void updatePaymentViewLine(){
    paymentView.writeLine(paymentModel.isDebtorSet(),paymentModel.getDepositNumber(),paymentModel.getAmount());
    }
    public void addDataToView(){
    paymentView.add(paymentModel.isDebtorSet(),paymentModel.getDepositNumber(),paymentModel.getAmount());
    }
    public static void updatePayment(){
   PaymentView.write();
    }
}
