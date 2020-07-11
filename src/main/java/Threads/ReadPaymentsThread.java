package Threads;

import model.PaymentModel;
import sevices.CalculationService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadPaymentsThread extends Thread {
    CalculationService calculation;
    List<PaymentModel> paymentModels = new ArrayList<>();
    private Thread t;
    private String threadName;
    private boolean isDone = false;
    private CountDownLatch countDownLatch;

    public ReadPaymentsThread(String name, CalculationService calculation, CountDownLatch countDownLatch) {
        threadName = name;
        this.calculation = calculation;
        this.countDownLatch = countDownLatch;
    }

    public void run() {

        paymentModels = calculation.addPaymentData();
        isDone = true;
        countDownLatch.countDown();
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public List<PaymentModel> getPayment() {
        return paymentModels;
    }
}

