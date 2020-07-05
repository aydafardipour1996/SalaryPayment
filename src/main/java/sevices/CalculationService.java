package sevices;

import model.PaymentModel;

import java.math.BigDecimal;

public class CalculationService {


    public void calculatePayments() {
        //some calculations
        setPayment(true, "100.1.2", new BigDecimal(100));
        setPayment(false, "100.1.4", new BigDecimal(20));
        setPayment(false, "100.1.6", new BigDecimal(50));
        setPayment(false, "100.1.7", new BigDecimal(30));

        WriteToFileService.updatePayment();
    }

    private void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addPayment(paymentModel);


    }
}
