package sevices;

import model.DepositModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

class ThreadDemo3 extends Thread {
    DepositService deposit;
    boolean isDone = false;
    List<DepositModel> depositModels = new ArrayList<>();
    CountDownLatch countDownLatch;
    private Thread t;
    private String threadName;

    ThreadDemo3(String name, DepositService deposit, CountDownLatch countDownLatch) {
        threadName = name;
        this.deposit = deposit;
        this.countDownLatch = countDownLatch;
    }

    public void run() {

        depositModels = deposit.getDeposits();
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

    public List<DepositModel> getDeposit() {
        return depositModels;
    }

}

