import model.DepositModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReadData {
    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final int NOT_FOUND = 0;
    private static final int CHARGED = 1;
    private static final int NOT_CHARGED = 2;
    static private List<String[]> res = new ArrayList<>(10);
    static private List<DepositModel> depositModels = new ArrayList<>();

    public void read() {
        Path DepositPath = Paths.get("data/Deposit.txt");

        try {

            Files.lines(DepositPath).forEach(line -> res.add(line.split("\\t")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public List<DepositModel> addData() {
        for (String[] line : res) {
            depositModels.add(new DepositModel(line[0], new BigDecimal(line[1])));
        }
        return depositModels;
    }

    public int isDebtorCharged(String debtorNumber) {
        int state = NOT_FOUND;

        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                if (depositModel.getAmount().compareTo(ZERO) > 0) {
                    state = CHARGED;
                } else {
                    state = NOT_CHARGED;
                }
            }

        }
        if (state == NOT_FOUND)
            System.out.println("Debtor not found!!");
        return state;
    }

    public BigDecimal getDebtorDeposit(String debtorNumber) {
        BigDecimal Deposit= new BigDecimal(0) ;
        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                Deposit = depositModel.getAmount();
            }

        }
        return Deposit;
    }


}
