package sevices;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class WriteToFileService {
    static Path pathTransaction = Paths.get("data/Transaction.txt");
    static Path pathPayment = Paths.get("data/Payment.txt");
    static Path pathDeposit = Paths.get("data/Deposit.txt");
    static List<String> transactionRes = new ArrayList<>();
    static List<String> paymentRes = new ArrayList<>();
    static List<String> depositRes = new ArrayList<>();
    String tab = "\t";

    private static void write(Path path, List<String> res, boolean append) {

        try {
            if (append) {
                Files.write(path, res, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } else {
                Files.write(path, res, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeTransaction() {
        write(pathTransaction, transactionRes, true);
    }

    public static void writePayment() {
        write(pathPayment, paymentRes, true);

    }

    public static void writeDeposit() {
        write(pathDeposit, depositRes, false);
    }

    public void addTransaction(String sender, String receiver, BigDecimal amount) {

        transactionRes.add(sender + tab + receiver + tab + amount);

    }

    public void addPayment(boolean isDebtor, String depositNumber, BigDecimal amount) {
        if (isDebtor) {
            paymentRes.add("debtor" + tab + depositNumber + tab + amount);
        } else
            paymentRes.add("creditor" + tab + depositNumber + tab + amount);
    }

    public void addDeposit(String depositNumber, BigDecimal amount) {

        depositRes.add(depositNumber + tab + amount);
    }
}
