package sevices;

import controller.DepositController;
import model.DepositModel;
import view.DepositView;

import java.math.BigDecimal;

public class DepositService {
    static DepositView depositView = new DepositView();

    public void setDepositData() {
        setDeposit("100.1.2", new BigDecimal(1000));
        setDeposit("100.1.3", new BigDecimal(0));
        setDeposit("100.1.5", new BigDecimal(0));
        setDeposit("100.1.6", new BigDecimal(10));
        DepositController depositController = new DepositController();
        depositController.updateDepositView();
    }

    private void setDeposit(String depositNumber, BigDecimal amount) {

        DepositModel depositModel = new DepositModel(depositNumber, amount);
        DepositController depositController = new DepositController(depositModel, depositView, new WriteToFileService());
        depositController.addDataToView();

    }
}
