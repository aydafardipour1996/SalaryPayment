package Threads;

import model.DepositModel;
import sevices.DepositService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ReadDepositsThread implements Runnable {
    DepositService deposit;
    List<DepositModel> depositModels = new ArrayList<>();
    CountDownLatch countDownLatch;
    private String threadName;

    public ReadDepositsThread(String name, DepositService deposit, CountDownLatch countDownLatch) {
        threadName = name;
        this.deposit = deposit;
        this.countDownLatch = countDownLatch;
    }

    public void run() {

        depositModels = deposit.getDeposits();
        countDownLatch.countDown();

    }


    public List<DepositModel> getDeposit() {
        return depositModels;
    }

}

