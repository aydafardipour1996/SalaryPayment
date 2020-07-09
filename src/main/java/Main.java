import sevices.PaymentService;


public class Main {

    public static void main(String[] args) {

        PaymentService paymentService = new PaymentService();
        paymentService.check();
        PaymentService.setDeposit();
        PaymentService.update();

    }


}
