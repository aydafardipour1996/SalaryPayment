package sevices;

import model.DepositModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DepositService {
    ReadDataService dataService = new ReadDataService();
public List<DepositModel> depositModels = new ArrayList<>();
    private boolean flag = false;

    public synchronized void setDepositData() {
        if (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!dataService.depositFileExists()) {
            setDeposit("100.1.2", new BigDecimal(1000));

            for (int deposit = 0; deposit < 10; deposit++) {

                setDeposit("10.20.100." + deposit, new BigDecimal(10));
                System.out.println("setting Deposit" + deposit);
            }
            WriteToFileService.updateDeposit();
        }
        flag = true;
        notify();
    }

    private void setDeposit(String depositNumber, BigDecimal amount) {

        DepositModel depositModel = new DepositModel(depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addDeposit(depositModel);

    }

    public synchronized List<DepositModel> getDeposits() {
        if (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        depositModels=dataService.addDepositData();
        flag = false;
        notify();


        return depositModels;
    }

    public synchronized List<DepositModel> getDeposit() {
        return depositModels;
    }
}
