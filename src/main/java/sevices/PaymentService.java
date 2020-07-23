package sevices;

import Threads.PaymentThread;
import exceptions.InsufficientFundsException;
import exceptions.NoDebtorFoundException;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaymentService {

    static private final String debtorNumber = "100.1.2";
    static CalculationService calculation = new CalculationService();
    static DepositService depositService = new DepositService();
    static ExecutorService executor = Executors.newFixedThreadPool(10);
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;
    private CountDownLatch countDown = new CountDownLatch(calculation.limit);

    public static void update() {

        WriteToFileService.updateTransaction();
        WriteToFileService.updateDeposit();

    }

    private static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);
        WriteToFileService writeToFileService = new WriteToFileService();

        writeToFileService.addTransaction(transactionModel);


    }

    public static void setDebtorDeposit() {


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


        setTransaction(debtorNumber, creditorNumber, amount);
    }

    public void check() throws NoDebtorFoundException {

        depositService.setDepositData();
        calculation.calculatePayments();



        depositModels = depositService.getDeposits();
        List<PaymentModel> paymentModels = calculation.addPaymentData();
        boolean found = false;
        for (DepositModel depositModel : depositModels) {
            if (depositModel.getDepositNumber().equals(debtorNumber)) {
                debtorDeposit = depositModel.getAmount();
                found = true;
            }
        }
        if (!found) {
            throw new NoDebtorFoundException("Debtor not found!!");
        }


        for (PaymentModel paymentModel : paymentModels) {
            if (!paymentModel.isDebtorSet()) {

                PaymentThread paymentThread = new PaymentThread(countDown, paymentModel.getDepositNumber(), paymentModel.getAmount());
                executor.execute(paymentThread);


            }

        }


        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();


    }

}



