package controller;

import View.PaymentView;
import model.PaymentModel;
import java.math.BigDecimal;

public class PaymentController {
    private final PaymentModel paymentModel;
    private final PaymentView paymentView;


    public PaymentController(PaymentModel paymentModel, PaymentView paymentView) {
        this.paymentModel = paymentModel;
        this.paymentView = paymentView;
    }

    public static void updatePayment() {
        PaymentView.write();
    }

    public boolean isPaymentDebtor() {
        return paymentModel.isDebtorSet();
    }

    public void setPaymentDebtor(boolean isDebtor) {
        paymentModel.setDebtor(isDebtor);
    }

    public String getPaymentDepositNumber() {
        return paymentModel.getDepositNumber();
    }

    public void setPaymentDepositNumber(String depositNumber) {
        paymentModel.setDepositNumber(depositNumber);
    }

    public BigDecimal getPaymentAmount() {
        return paymentModel.getAmount();
    }

    public void setPaymentAmount(BigDecimal amount) {
        paymentModel.setAmount(amount);
    }

    public void updatePaymentViewLine() {
        paymentView.writeLine(paymentModel.isDebtorSet(), paymentModel.getDepositNumber(), paymentModel.getAmount());
    }

    public void addDataToView() {
        paymentView.add(paymentModel.isDebtorSet(), paymentModel.getDepositNumber(), paymentModel.getAmount());
    }
}
