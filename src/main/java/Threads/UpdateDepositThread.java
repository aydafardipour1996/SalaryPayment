package Threads;

import sevices.WriteToFileService;

public class UpdateDepositThread extends Thread {

    private Thread t;
    private String threadName;

    public UpdateDepositThread(String name) {
        threadName = name;

    }

    public void run() {
        WriteToFileService.updateDeposit();

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
