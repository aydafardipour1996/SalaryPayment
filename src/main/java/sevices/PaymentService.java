package sevices;

import Threads.*;
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

public class PaymentService {

    static private final String debtorNumber = "100.1.2";
    static CalculationService calculation = new CalculationService();
    static DepositService depositService = new DepositService();
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;

    public static void update() {
        WriteTransactionsThread writeTransactionsThread = new WriteTransactionsThread("transaction");
        UpdateDepositThread updateDepositThread = new UpdateDepositThread("update deposit");
        writeTransactionsThread.start();
        updateDepositThread.start();
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
        CountDownLatch countDownLatch = new CountDownLatch(2);
        WriteDepositThread T2 = new WriteDepositThread("Thread Deposit2 ", depositService);
        ReadDepositsThread T3 = new ReadDepositsThread("Thread Deposit3 ", depositService, countDownLatch);


        T2.start();

        CalculationThread T0 = new CalculationThread("Thread Payment0", calculation);
        ReadPaymentsThread T1 = new ReadPaymentsThread("Thread Payment1", calculation, countDownLatch);

        T0.start();


        T3.start();
        T1.start();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        depositModels = T3.getDeposit();
        List<PaymentModel> paymentModels = T1.getPayment();
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

}



