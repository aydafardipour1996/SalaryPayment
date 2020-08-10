package sevices;

import model.PaymentModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CalculationService {

    public int limit = 15;
    public int start = 5;

    public void calculatePayments() throws IOException {
        if (ReadDataService.paymentFileExists()) {
            ReadDataService.removePayments();
        }
        //some calculations

        WriteToFileService.openPaymentChannel();


        int value = 10;
        setPayment(true, "100.1.2", new BigDecimal((limit - start) * value));

        for (int deposit = start; deposit < limit; deposit++) {

            setPayment(false, "10.20.100." + deposit, new BigDecimal(value));

        }

        // WriteToFileService.updatePayment();

        try {
            WriteToFileService.paymentFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPayment(boolean isDebtor, String depositNumber, BigDecimal amount) throws IOException {

        PaymentModel paymentModel = new PaymentModel(isDebtor, depositNumber, amount);

        WriteToFileService.writeFileChannelLine(paymentModel.toString(), WriteToFileService.paymentFileChannel);


    }

    public List<PaymentModel> addPaymentData() {


        ReadDataService dataService = new ReadDataService();

        return dataService.getPaymentData();
    }


}
