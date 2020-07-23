package sevices;

import model.DepositModel;
import model.PaymentModel;
import model.TransactionModel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class WriteToFileService {
    static Path pathTransaction = Paths.get("data/Transaction.txt");
    static Path pathPayment = Paths.get("data/Payment.txt");
    static Path pathDeposit = Paths.get("data/Deposit.txt");
    static List<String> transactionRes = new CopyOnWriteArrayList<>();
    static List<String> paymentRes = new ArrayList<>();
    static List<String> depositRes = new CopyOnWriteArrayList<>();
    static String newLine = System.getProperty("line.separator");
    int i = 0;

    public static void writeFileChannel(Path path, List<String> res, Set<StandardOpenOption> options) throws IOException {
        String input = "";
        for (String re : res) {
            input = input + re + newLine;
        }
        byte[] byteArray = input.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        FileChannel fileChannel = FileChannel.open(path, options);
        fileChannel.write(buffer);
        fileChannel.close();
    }


    public static void updateTransaction() {

        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);

        try {

            writeFileChannel(pathTransaction, transactionRes, options);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public static void updatePayment() {

        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.WRITE);

        try {

            writeFileChannel(pathPayment, paymentRes, options);

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    public static void updateDeposit() {

        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.WRITE);

        try {

            writeFileChannel(pathDeposit, depositRes, options);
            depositRes.clear();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void addTransaction(TransactionModel transactionModel) {

        transactionRes.add(transactionModel.toString());


    }

    public void addPayment(PaymentModel paymentModel) {

        paymentRes.add(paymentModel.toString());

    }

    public void addDeposit(DepositModel depositModel) {

        depositRes.add(depositModel.toString());

    }

}
