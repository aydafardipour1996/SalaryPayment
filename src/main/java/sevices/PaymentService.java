package sevices;

import exceptions.InsufficientFundsException;
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

    public void check() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ThreadDemo2 T2 = new ThreadDemo2("Thread Deposit2 ", depositService);
        ThreadDemo3 T3 = new ThreadDemo3("Thread Deposit3 ", depositService, countDownLatch);
        //DepositListThread T4 = new DepositListThread("Thread Deposit3 ", depositService,countDownLatch);

        T2.start();

        ThreadDemo0 T0 = new ThreadDemo0("Thread Payment0", calculation);
        ThreadDemo1 T1 = new ThreadDemo1("Thread Payment1", calculation, countDownLatch);
        //  PaymentListThread T00 = new PaymentListThread("Thread Payment00", calculation,countDownLatch);
        T0.start();


        T3.start();
        T1.start();


        try {

            T1.join();
            System.out.println("lamazhab1");
            T3.join();
            System.out.println("lamazhab3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        depositModels = T3.getDeposit();
        List<PaymentModel> paymentModels = T1.getPayment();

        debtorDeposit = depositModels.get(0).getAmount();
        for (PaymentModel paymentModel : paymentModels) {
            System.out.println("lalalalal" + paymentModel);
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



