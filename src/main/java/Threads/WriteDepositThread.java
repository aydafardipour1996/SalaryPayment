package Threads;

import sevices.DepositService;

public class WriteDepositThread extends Thread {
    DepositService deposit;
    private Thread t;
    private String threadName;

    public WriteDepositThread(String name, DepositService deposit) {
        threadName = name;
        this.deposit = deposit;
    }

    public void run() {

        deposit.setDepositData();

        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}

