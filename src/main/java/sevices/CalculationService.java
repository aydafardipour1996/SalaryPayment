package sevices;

import model.PaymentModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculationService {
    public List<PaymentModel> paymentModels = new ArrayList<>();


    public void calculatePayments() {
        //some calculations


        int limit = 20;
        int value = 2;
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
        paymentModels = dataService.addPaymentData();

        return paymentModels;
    }


}
