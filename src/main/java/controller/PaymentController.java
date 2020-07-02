package controller;

import view.PaymentView;
import model.PaymentModel;
import sevices.WriteToFileService;

import java.math.BigDecimal;

public class PaymentController {
    private PaymentModel paymentModel;
    private PaymentView paymentView;
    private WriteToFileService service;

    public PaymentController() {
    }

    public PaymentController(PaymentModel paymentModel, PaymentView paymentView, WriteToFileService service) {
        this.paymentModel = paymentModel;
        this.paymentView = paymentView;
        this.service = service;
    }

    public void updatePayment() {
        WriteToFileService.writePayment();
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
        service.addPayment(paymentModel.isDebtorSet(), paymentModel.getDepositNumber(), paymentModel.getAmount());
    }
}
