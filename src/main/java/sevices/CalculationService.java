package sevices;

import model.PaymentModel;

import java.math.BigDecimal;
import java.util.List;

public class CalculationService {

    public int limit = 10;

    public void calculatePayments() {
        //some calculations


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

    public List<PaymentModel> addPaymentData() {


        ReadDataService dataService = new ReadDataService();
        List<PaymentModel> paymentModels = dataService.addPaymentData();

        return paymentModels;
    }


}
