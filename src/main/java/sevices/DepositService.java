package sevices;

import model.DepositModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DepositService {
    public List<DepositModel> depositModels = new ArrayList<>();
    ReadDataService dataService = new ReadDataService();


    public synchronized void setDepositData() {


        if (!dataService.depositFileExists()) {
            setDeposit("100.1.2", new BigDecimal(1000));

            for (int deposit = 0; deposit < 10; deposit++) {

                setDeposit("10.20.100." + deposit, new BigDecimal(10));

            }
            WriteToFileService.updateDeposit();
        }

    }

    private void setDeposit(String depositNumber, BigDecimal amount) {

        DepositModel depositModel = new DepositModel(depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addDeposit(depositModel);

    }

    public List<DepositModel> getDeposits() {

        depositModels = dataService.addDepositData();


        return depositModels;
    }


}
