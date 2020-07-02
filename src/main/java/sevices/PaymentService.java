package sevices;

import controller.DepositController;
import controller.PaymentController;
import controller.TransactionController;
import exceptions.InsufficientFundsException;
import exceptions.NoDebtorFoundException;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;
import view.DepositView;
import view.PaymentView;
import view.TransactionView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentService {

    static private final String debtorNumber = "100.1.2";
    static PaymentView paymentView = new PaymentView();
    static TransactionView transactionView = new TransactionView();
    static TransactionController transactionController;
    static PaymentController paymentController;
    static DepositController depositController;
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;
    static private BigDecimal debtorDepositSum = BigDecimal.ZERO;

    public static void check() {

        ReadData data = new ReadData();
        depositModels = data.addDepositData();

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
            payDept("200.0.0", new BigDecimal(30));
        } catch (InsufficientFundsException e) {
            System.out.println("not enough balance, you are short " + e.getAmount());
            e.printStackTrace();
        }

        PaymentController paymentController = new PaymentController(new PaymentModel(true, debtorNumber, debtorDepositSum), paymentView, new WriteToFileService());

        if (!debtorDepositSum.equals(BigDecimal.ZERO)) {
            paymentController.updatePaymentViewLine();
        }

    }

    private static void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);
        PaymentController paymentController = new PaymentController(paymentModel, paymentView, new WriteToFileService());
        paymentController.addDataToView();
    }

    public static void update() {
        paymentController.updatePayment();
        depositController.updateDepositView();
        transactionController.updateTransaction();
    }

    private static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);
        TransactionController transactionController = new TransactionController(transactionModel, transactionView, new WriteToFileService());
        transactionController.addDataToView();

    }

    public static void setDeposit() {
        DepositView depositView = new DepositView();
        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {
                depositModel.setAmount(debtorDeposit);

            }
            DepositController depositController = new DepositController(depositModel, depositView, new WriteToFileService());
            depositController.addDataToView();
        }
    }

    public static void payDept(String creditorNumber, BigDecimal amount) throws InsufficientFundsException {
        boolean exists = false;
        if (amount.compareTo(debtorDeposit) <= 0) {
            setPayment(false, creditorNumber, amount);
            setTransaction(debtorNumber, creditorNumber, amount);
            debtorDeposit = debtorDeposit.subtract(amount);
            debtorDepositSum = debtorDepositSum.add(amount);
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
            throw new InsufficientFundsException(needs);

        }

    }
}


