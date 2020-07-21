package sevices;

import Threads.ReadDepositsThread;
import Threads.ReadPaymentsThread;
import Threads.UpdateDepositThread;
import Threads.WriteTransactionsThread;
import exceptions.InsufficientFundsException;
import exceptions.NoDebtorFoundException;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

public class PaymentService {

    static private final String debtorNumber = "100.1.2";
    static CalculationService calculation = new CalculationService();
    static DepositService depositService = new DepositService();
    static ExecutorService executor = Executors.newFixedThreadPool(5);
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;

    public static void update() {

        WriteTransactionsThread writeTransactionsThread = new WriteTransactionsThread("transaction");
        UpdateDepositThread updateDepositThread = new UpdateDepositThread("update deposit");
        executor.execute(writeTransactionsThread);
        executor.execute(updateDepositThread);
        executor.shutdown();
    }

    private static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addTransaction(transactionModel);

    }

    public static void setDebtorDeposit() {
        executor.execute(() -> {
            System.out.println("deposit set");
            for (DepositModel depositModel : depositModels) {
                if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                    depositModel.setAmount(debtorDeposit);

                }
                WriteToFileService writeToFileService = new WriteToFileService();
                writeToFileService.addDeposit(depositModel);
            }
        });

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

        CountDownLatch countDownLatch = new CountDownLatch(2);

        ReadDepositsThread readDepositsThread = new ReadDepositsThread("Thread Deposit3 ", depositService, countDownLatch);
        ReadPaymentsThread readPaymentsThread = new ReadPaymentsThread("Thread Payment1", calculation, countDownLatch);


        executor.execute(readDepositsThread);
        executor.execute(readPaymentsThread);

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        depositModels = readDepositsThread.getDeposit();
        List<PaymentModel> paymentModels = readPaymentsThread.getPayment();
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
        Set<Callable<String>> callables = new HashSet<Callable<String>>();


        for (PaymentModel paymentModel : paymentModels) {
            if (!paymentModel.isDebtorSet()) {

                callables.add(() -> {
                    try {
                        payDept(paymentModel.getDepositNumber(), paymentModel.getAmount());
                        System.out.println("payed " + paymentModel.getDepositNumber());
                    } catch (InsufficientFundsException e) {
                        e.printStackTrace();
                    }
                    return "done";
                });


            }

        }

        List<Future<String>> futures = null;
        try {
            futures = executor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean done = false;
/*        for (Future<String> future : futures) {
            try {
                if (future.get().equals("done")) {
                    done = true;
                } else {
                    done = false;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }*/


    }

}



