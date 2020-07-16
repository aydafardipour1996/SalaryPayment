package Threads;

import sevices.WriteToFileService;

public class WriteTransactionsThread implements Runnable {


    private String threadName;

    public WriteTransactionsThread(String name) {
        threadName = name;

    }

    public void run() {
        WriteToFileService.updateTransaction();
    }


}
