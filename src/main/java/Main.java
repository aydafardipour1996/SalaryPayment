import exceptions.NoDebtorFoundException;
import sevices.PaymentService;


public class Main {

    public static void main(String[] args) {

        PaymentService paymentService = new PaymentService();
        try {
            paymentService.check();
        } catch (NoDebtorFoundException e) {
            e.printStackTrace();
        }
        PaymentService.setDeposit();
        PaymentService.update();

    }


}
