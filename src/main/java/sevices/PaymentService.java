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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static sevices.Constants.newLine;
import static sevices.Constants.space;


public class PaymentService {

    static final String debtorNumber = "100.1.2";
    static CalculationService calculation = new CalculationService();
    static DepositService depositService = new DepositService();
    static ExecutorService executor = Executors.newFixedThreadPool(20);
    static Long lastPosition;
    static private BigDecimal debtorDeposit;
    private static DepositModel debtor;
    private final CountDownLatch countDown = new CountDownLatch(calculation.limit - calculation.start);

    private synchronized static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);

        try {
            WriteToFileService.writeFileChannelLine(transactionModel.toString(), WriteToFileService.transactionFileChannel);


        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void setDebtorDeposit() throws IOException {

        debtor.setDeposit(debtorDeposit);
        WriteToFileService.updateFileChannelLine(debtor.toString() + space, debtor.getPosition());


    }

    public static synchronized void payDept(String creditorNumber, BigDecimal amount) throws InsufficientFundsException, IOException, NoDebtorFoundException {


        if (amount.compareTo(debtorDeposit) <= 0) {


            debtorDeposit = debtorDeposit.subtract(amount);

            lastPosition = WriteToFileService.getPosition();
            DepositModel depositModel1 = new DepositModel(creditorNumber, ReadDataService.readDeposit(creditorNumber).add(amount), ReadDataService.readPosition(creditorNumber));
            if (depositModel1.getPosition() == lastPosition) {
                WriteToFileService.updateFileChannelLine(depositModel1.toString() + space + newLine, depositModel1.getPosition());
                WriteToFileService.writeFileChannelLine(depositModel1.positionString(), WriteToFileService.positionFileChannel);
            } else {

                WriteToFileService.updateFileChannelLine(depositModel1.toString(), depositModel1.getPosition());

            }

            setDebtorDeposit();
        } else {
            BigDecimal needs = amount.subtract(debtorDeposit);
            throw new InsufficientFundsException("not enough balance!! " + needs + " short!", needs);

        }


        setTransaction(debtorNumber, creditorNumber, amount);
    }

    private static void updateDebtorDeposit() throws NoDebtorFoundException, IOException {

        debtorDeposit = ReadDataService.readDeposit(debtorNumber);
        if (debtorDeposit.equals(new BigDecimal(-1))) {
            throw new NoDebtorFoundException("Debtor not found!!");
        }

    }

    public void check() throws NoDebtorFoundException, IOException {

        depositService.setDepositData();
        calculation.calculatePayments();
        List<PaymentModel> paymentModels = calculation.addPaymentData();
        updateDebtorDeposit();
        debtor = new DepositModel(debtorNumber, debtorDeposit, ReadDataService.readPosition(debtorNumber));
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



