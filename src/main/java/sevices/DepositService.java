package sevices;

import model.DepositModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class DepositService {
    ReadDataService dataService = new ReadDataService();
    long position = 0;

    public void setDepositData() {


        if (!dataService.depositFileExists()) {
            try {
                WriteToFileService.openDepositChannel();
                WriteToFileService.openPositionChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setDeposit("100.1.2", new BigDecimal(1000));


            for (int deposit = 0; deposit < 10; deposit++) {

                setDeposit("10.20.100." + deposit, new BigDecimal(50));

            }

        }


    }

    private void setDeposit(String depositNumber, BigDecimal amount) {
        String space = "               ";
        DepositModel depositModel = new DepositModel(depositNumber, amount, position);

        try {
            //   WriteToFileService.updateFileChannelLine(depositModel.toString(), position);
            WriteToFileService.writeFileChannelLine(depositModel.toString() + space, WriteToFileService.depositFileChannel);
            WriteToFileService.writeFileChannelLine(depositModel.positionString(), WriteToFileService.positionFileChannel);
            position = WriteToFileService.getPosition();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
