import Exceptions.NoDebtorFoundException;
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


    public BigDecimal getDebtorDeposit(String debtorNumber) throws NoDebtorFoundException {
        boolean found = false;
        BigDecimal Deposit = new BigDecimal(0);
        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                Deposit = depositModel.getAmount();
                found = true;
            }

        }
        if (!found) {
            throw new NoDebtorFoundException("Debtor not found!!");
        }
        return Deposit;
    }


}
