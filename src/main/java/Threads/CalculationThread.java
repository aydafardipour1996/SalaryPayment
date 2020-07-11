package Threads;

import sevices.CalculationService;

public class CalculationThread extends Thread {
    CalculationService calculation;
    private Thread t;
    private String threadName;

    public CalculationThread(String name, CalculationService calculation) {
        threadName = name;
        this.calculation = calculation;
    }

    public void run() {

        calculation.calculatePayments();

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

