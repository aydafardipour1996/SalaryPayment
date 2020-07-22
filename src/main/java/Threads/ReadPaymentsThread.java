package Threads;

import model.PaymentModel;
import sevices.CalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadPaymentsThread implements Runnable {
    CalculationService calculation;
    List<PaymentModel> paymentModels = new ArrayList<>();

    private String threadName;
    private CountDownLatch countDownLatch;

    public ReadPaymentsThread(String name, CalculationService calculation, CountDownLatch countDownLatch) {
        threadName = name;
        this.calculation = calculation;
        this.countDownLatch = countDownLatch;
    }

    public void run() {

        paymentModels = calculation.addPaymentData();
        System.out.println("done  " + threadName);
        countDownLatch.countDown();

    }


    public List<PaymentModel> getPayment() {
        return paymentModels;
    }
}

