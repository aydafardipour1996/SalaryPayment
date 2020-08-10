package Threads;


import exceptions.InsufficientFundsException;
import exceptions.NoDebtorFoundException;
import sevices.PaymentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

public class PaymentThread implements Runnable {
    CountDownLatch countDownLatch;
    String creditorNumber;
    BigDecimal amount;


    public PaymentThread(CountDownLatch countDownLatch, String creditorNumber, BigDecimal amount) {

        this.amount = amount;
        this.creditorNumber = creditorNumber;
        this.countDownLatch = countDownLatch;

    }

    public void run() {
        try {
            PaymentService.payDept(creditorNumber, amount);
        } catch (InsufficientFundsException | IOException | NoDebtorFoundException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();

    }
}
