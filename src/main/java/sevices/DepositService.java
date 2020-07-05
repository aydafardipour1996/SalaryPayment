package sevices;

import model.DepositModel;

import java.math.BigDecimal;

public class DepositService {


    public void setDepositData() {

        setDeposit("100.1.2", new BigDecimal(100000));

        for (int deposit = 0; deposit < 1000; deposit++) {

            setDeposit("10.20.100." + deposit, new BigDecimal(10));

        }
        WriteToFileService.updateDeposit();
    }

    private void setDeposit(String depositNumber, BigDecimal amount) {

        DepositModel depositModel = new DepositModel(depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addDeposit(depositModel);

    }
}
