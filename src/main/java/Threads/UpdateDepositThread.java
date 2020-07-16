package Threads;

import sevices.WriteToFileService;

public class UpdateDepositThread implements Runnable {

    private String threadName;

    public UpdateDepositThread(String name) {
        threadName = name;

    }

    public void run() {
        WriteToFileService.updateDeposit();

    }


}
