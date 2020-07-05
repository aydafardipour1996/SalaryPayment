package sevices;

import exceptions.InsufficientFundsException;
import exceptions.NoDebtorFoundException;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentService {

    static private final String debtorNumber = "100.1.2";
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;

    public static void check() {

        ReadDataService data = new ReadDataService();

        if (!data.depositFileExists()) {

            DepositService depositService = new DepositService();
            depositService.setDepositData();

        }

        CalculationService calculation = new CalculationService();
        calculation.calculatePayments();

        depositModels = data.addDepositData();
        List<PaymentModel> paymentModels = data.addPaymentData();


        try {
            debtorDeposit = data.getDebtorDeposit(debtorNumber);
        } catch (NoDebtorFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            for (PaymentModel paymentModel : paymentModels) {
                if (!paymentModel.isDebtorSet()) {
                    payDept(paymentModel.getDepositNumber(), paymentModel.getAmount());
                }

            }

        } catch (InsufficientFundsException e) {

            e.printStackTrace();
        }


    }


    public static void update() {
        WriteToFileService.updateDeposit();
        WriteToFileService.updateTransaction();
    }

    private static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addTransaction(transactionModel);

    }

    public static void setDeposit() {

        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {
                depositModel.setAmount(debtorDeposit);

            }
            WriteToFileService writeToFileService = new WriteToFileService();
            writeToFileService.addDeposit(depositModel);
        }
    }

    public static void payDept(String creditorNumber, BigDecimal amount) throws InsufficientFundsException {
        boolean exists = false;
        if (amount.compareTo(debtorDeposit) <= 0) {

            setTransaction(debtorNumber, creditorNumber, amount);
            debtorDeposit = debtorDeposit.subtract(amount);

            for (DepositModel depositModel : depositModels) {

                if (Objects.equals(depositModel.getDepositNumber(), creditorNumber)) {
                    depositModel.setAmount(depositModel.getAmount().add(amount));
                    exists = true;
                }
            }
            if (!exists) {
                depositModels.add(new DepositModel(creditorNumber, amount));
            }

        } else {
            BigDecimal needs = amount.subtract(debtorDeposit);
            throw new InsufficientFundsException("not enough balance!! " + needs + " short!", needs);

        }

    }

}



