package sevices;

import model.PaymentModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculationService {
    public List<PaymentModel> paymentModels = new ArrayList<>();
    boolean flag = false;

    public synchronized void calculatePayments() {
        //some calculations
        if (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        int limit = 10;
        int value = 10;
        setPayment(true, "100.1.2", new BigDecimal(limit * value));

        for (int deposit = 0; deposit < limit; deposit++) {

            setPayment(false, "10.20.100." + deposit, new BigDecimal(value));
            System.out.println("setting Payment");
        }

        WriteToFileService.updatePayment();
        flag = true;
        notify();
    }

    private void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);
        WriteToFileService writeToFileService = new WriteToFileService();
        writeToFileService.addPayment(paymentModel);


    }

    public synchronized List<PaymentModel> addPaymentData() {

        if (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ReadDataService dataService = new ReadDataService();
        paymentModels = dataService.addPaymentData();
        flag = false;
        notify();


        return paymentModels;
    }

    public synchronized List<PaymentModel> getPayment() {

        return paymentModels;

    }


}
