package sevices;

import Threads.PaymentThread;
import exceptions.InsufficientFundsException;
import exceptions.NoDebtorFoundException;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PaymentService {

    static private final String debtorNumber = "100.1.2";
    static CalculationService calculation = new CalculationService();
    static DepositService depositService = new DepositService();
    static ExecutorService executor = Executors.newFixedThreadPool(20);
    static long position = 0;
    static Long lastPosition;
    static String space = "               ";
    static private List<DepositModel> depositModels = new CopyOnWriteArrayList<>();
    static private BigDecimal debtorDeposit;
    private CountDownLatch countDown = new CountDownLatch(calculation.limit - calculation.start);


    private synchronized static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);

        try {
            WriteToFileService.writeFileChannelLine(transactionModel.toString(), WriteToFileService.transactionFileChannel);


        } catch (IOException e) {

            e.printStackTrace();

        }


    }

    public static void setDebtorDeposit() throws IOException {


        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                depositModel.setDeposit(debtorDeposit);

            }
            WriteToFileService.updateFileChannelLine(depositModel.toString() + space, depositModel.getPosition());

        }


    }

    public static synchronized void payDept(String creditorNumber, BigDecimal amount) throws InsufficientFundsException, IOException {
        boolean exists = false;

        String newLine = System.getProperty("line.separator");

        if (amount.compareTo(debtorDeposit) <= 0) {


            debtorDeposit = debtorDeposit.subtract(amount);

            for (DepositModel depositModel : depositModels) {


                if (Objects.equals(depositModel.getDepositNumber(), creditorNumber)) {


                    depositModel.setDeposit(depositModel.getDeposit().add(amount));

                    exists = true;


                    WriteToFileService.updateFileChannelLine(depositModel.toString(), depositModel.getPosition());


                }
            }
            if (!exists) {
                lastPosition = WriteToFileService.getPosition();
                DepositModel depositModel = new DepositModel(creditorNumber, amount, lastPosition);
                depositModels.add(depositModel);
                WriteToFileService.updateFileChannelLine(depositModel.toString() + space + newLine, depositModel.getPosition());
                WriteToFileService.writeFileChannelLine(depositModel.positionString(), WriteToFileService.positionFileChannel);
            }
            setDebtorDeposit();
        } else {
            BigDecimal needs = amount.subtract(debtorDeposit);
            throw new InsufficientFundsException("not enough balance!! " + needs + " short!", needs);

        }


        setTransaction(debtorNumber, creditorNumber, amount);
    }

    public void check() throws NoDebtorFoundException, IOException {

        depositService.setDepositData();
        calculation.calculatePayments();


        depositModels = depositService.getDeposits();
        List<PaymentModel> paymentModels = calculation.addPaymentData();
        boolean found = false;

        for (DepositModel depositModel : depositModels) {
            if (depositModel.getDepositNumber().equals(debtorNumber)) {
                debtorDeposit = depositModel.getDeposit();
                found = true;
            }
        }
        if (!found) {
            throw new NoDebtorFoundException("Debtor not found!!");
        }


        WriteToFileService.openTransactionChannel();
        WriteToFileService.openDepositChannel();
        WriteToFileService.openPositionChannel();


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

        WriteToFileService.closeChannels();


    }


}



