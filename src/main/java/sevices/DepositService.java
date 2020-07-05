package sevices;

import model.DepositModel;

import java.math.BigDecimal;

public class DepositService {


    public void setDepositData() {
        setDeposit("100.1.2", new BigDecimal(1000));
        setDeposit("100.1.3", new BigDecimal(0));
        setDeposit("100.1.5", new BigDecimal(0));
        setDeposit("100.1.6", new BigDecimal(10));

        WriteToFileService.updateDeposit();
    }

    private void setDeposit(String depositNumber, BigDecimal amount) {

        DepositModel depositModel = new DepositModel(depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addDeposit(depositModel);

    }
}
