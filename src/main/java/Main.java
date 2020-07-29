import exceptions.NoDebtorFoundException;
import sevices.PaymentService;

import java.io.IOException;


public class Main {

    public static void main(String[] args) {

        PaymentService paymentService = new PaymentService();
        try {
            paymentService.check();
        } catch (NoDebtorFoundException | IOException e) {
            e.printStackTrace();
        }


    }


}
