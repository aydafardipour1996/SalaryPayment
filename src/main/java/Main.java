import Exceptions.InsufficientFundsException;
import View.DepositView;
import View.PaymentView;
import View.TransactionView;
import controller.DepositController;
import controller.PaymentController;
import controller.TransactionController;
import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    static private final String debtorNumber = "100.1.2";
    static PaymentView paymentView = new PaymentView();
    static TransactionView transactionView = new TransactionView();
    static private List<String[]> res = new ArrayList();
    static private List<TransactionModel> transactionModels = new ArrayList();
    static private List<DepositModel> depositModels = new ArrayList();
    static private BigDecimal debtorDeposit;
    static private BigDecimal debtorDepositSum= new BigDecimal(0);

    public static void main(String[] args) {

        ReadData data = new ReadData();
        data.read();
        depositModels = data.addData();

       // System.out.println(data.isDebtorCharged(debtorNumber));
        debtorDeposit = data.getDebtorDeposit(debtorNumber);

        try {
            payDept("100.8.8", new BigDecimal(10));
            payDept("200.0.0", new BigDecimal(40));
        } catch (InsufficientFundsException e) {
            System.out.println("not enough balance, you are short "+ e.getAmount());
            e.printStackTrace();
        }

        PaymentController paymentController = new PaymentController(new PaymentModel(true, debtorNumber, debtorDepositSum), paymentView);
        paymentController.updatePaymentViewLine();
        setDeposit();
        update();
    }

    private static void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);
        PaymentController paymentController = new PaymentController(paymentModel, paymentView);
        paymentController.addDataToView();


    }

    private static void update() {
        PaymentController.updatePayment();
        TransactionController.updateTransaction();
        DepositController.updateDepositView();
    }

    private static void setTransaction(String sender, String receiver, BigDecimal amount) {
        TransactionModel transactionModel = new TransactionModel(sender, receiver, amount);
        //   transactionModels.add(transactionModel);
        TransactionController transactionController = new TransactionController(transactionModel, transactionView);
        transactionController.addDataToView();

    }

    private static void setDeposit() {
        DepositView depositView = new DepositView();
        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {
                depositModel.setAmount(debtorDeposit);

            }
            DepositController depositController = new DepositController(depositModel, depositView);
            depositController.addDataToView();
        }
    }

    private static void payDept (String creditorNumber, BigDecimal amount) throws InsufficientFundsException {
        boolean exists = false;
        if (amount.compareTo(debtorDeposit)<=0 ) {
            setPayment(false, creditorNumber, amount);
            setTransaction(debtorNumber, creditorNumber, amount);
            debtorDeposit = debtorDeposit.subtract(amount);
            debtorDepositSum =debtorDepositSum.add(amount);
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

/*private static DepositModel readDeposit(String number){
    Path DepositPath = Paths.get("data/Deposit.txt");
    try {

        Files.lines(DepositPath)
                .filter(line -> line.startsWith(number)).forEach(line -> {
       String s=line;
        });



    } catch (IOException ex) {
        ex.printStackTrace();//handle exception here
    }
    return
}*/

}
