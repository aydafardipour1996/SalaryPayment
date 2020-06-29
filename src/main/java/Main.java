import Exceptions.InsufficientFundsException;
import Exceptions.NoDebtorFoundException;
import View.DepositView;
import View.PaymentView;
import View.TransactionView;
import controller.DepositController;
import controller.PaymentController;
import controller.TransactionController;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;
import sevices.WriteToFileService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Main {

    static private final String debtorNumber = "100.1.2";
    static PaymentView paymentView = new PaymentView();
    static TransactionView transactionView = new TransactionView();
    static TransactionController transactionController;
    static PaymentController paymentController;
    static DepositController depositController;
    static private List<String[]> res = new ArrayList();
    static private List<TransactionModel> transactionModels = new ArrayList();
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;
    static private BigDecimal debtorDepositSum = new BigDecimal(0);

    public static void main(String[] args) {

        ReadData data = new ReadData();
        // data.read();
        try {
            data.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        depositModels = data.addData();
        transactionController = new TransactionController();
        paymentController = new PaymentController();
        depositController = new DepositController();

        try {
            debtorDeposit = data.getDebtorDeposit(debtorNumber);
        } catch (NoDebtorFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try {
            payDept("100.8.8", new BigDecimal(10));
            payDept("200.0.0", new BigDecimal(40));
        } catch (InsufficientFundsException e) {
            System.out.println("not enough balance, you are short " + e.getAmount());
            e.printStackTrace();
        }

        PaymentController paymentController = new PaymentController(new PaymentModel(true, debtorNumber, debtorDepositSum), paymentView, new WriteToFileService());
        if (!debtorDepositSum.equals(BigDecimal.ZERO)) {
            paymentController.updatePaymentViewLine();
        }
        setDeposit();
        update();
    }

    private static void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);
        PaymentController paymentController = new PaymentController(paymentModel, paymentView, new WriteToFileService());
        paymentController.addDataToView();
    }

    private static void update() {
        paymentController.updatePayment();
        depositController.updateDepositView();
        transactionController.updateTransaction();
    }

    private static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);
        TransactionController transactionController = new TransactionController(transactionModel, transactionView, new WriteToFileService());
        transactionController.addDataToView();

    }

    private static void setDeposit() {
        DepositView depositView = new DepositView();
        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {
                depositModel.setAmount(debtorDeposit);

            }
            DepositController depositController = new DepositController(depositModel, depositView, new WriteToFileService());
            depositController.addDataToView();
        }
    }

    private static void payDept(String creditorNumber, BigDecimal amount) throws InsufficientFundsException {
        boolean exists = false;
        if (amount.compareTo(debtorDeposit) <= 0) {
            setPayment(false, creditorNumber, amount);
            setTransaction(debtorNumber, creditorNumber, amount);
            debtorDeposit = debtorDeposit.subtract(amount);
            debtorDepositSum = debtorDepositSum.add(amount);
            for (int i = 0; i < depositModels.size(); i++) {

                if (Objects.equals(depositModels.get(i).getDepositNumber(), creditorNumber)) {
                    depositModels.get(i).setAmount(depositModels.get(i).getAmount().add(amount));
                    exists = true;
                }
            }
            if (!exists) {
                depositModels.add(new DepositModel(creditorNumber, amount));
            }

        } else {
            BigDecimal needs = amount.subtract(debtorDeposit);
            throw new InsufficientFundsException(needs);

        }

    }
}
