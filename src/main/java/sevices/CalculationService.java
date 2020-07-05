package sevices;

import model.PaymentModel;

import java.math.BigDecimal;

public class CalculationService {


    public void calculatePayments() {
        //some calculations
        int limit = 100;
        int value = 10;
        setPayment(true, "100.1.2", new BigDecimal(limit * value));

        for (int deposit = 0; deposit < limit; deposit++) {

            setPayment(false, "10.20.100." + deposit, new BigDecimal(value));

        }

        WriteToFileService.updatePayment();
    }

    private void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addPayment(paymentModel);


    }
}
