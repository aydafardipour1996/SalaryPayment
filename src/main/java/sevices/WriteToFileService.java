package sevices;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WriteToFileService {
    static Path pathTransaction = Paths.get("data/Transaction.txt");
    static Path pathPayment = Paths.get("data/Payment.txt");
    static Path pathDeposit = Paths.get("data/Deposit.txt");
    static List<String> transactionRes = new ArrayList<>();
    static List<String> paymentRes = new ArrayList<>();
    static List<String> depositRes = new ArrayList<>();
    static String transactions = "";
    static String deposits = "";
    static String payments = "";
    static String newLine = System.getProperty("line.separator");
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

    public static void writeFileChannel(Path path, List<String> res, Set<StandardOpenOption> options) throws IOException {
        String input = "";
        for (int i = 0; i < res.size(); i++) {
            input = input + res.get(i) + newLine;
        }
        byte[] byteArray = input.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        FileChannel fileChannel = FileChannel.open(path, options);
        fileChannel.write(buffer);
        fileChannel.close();
    }

    private static void writeFile(List<String> res, Path path) throws IOException {
        String input = "";
        for (int i = 0; i < res.size(); i++) {
            input = input + res.get(i) + newLine;
        }
        byte[] byteArray = input.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);

        AsynchronousFileChannel channel;
        channel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        CompletionHandler handler = new CompletionHandler() {
            @Override
            public void completed(Object result, Object attachment) {
                System.out.println(attachment + " completed and " + result + " bytes are written.");
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println(attachment + " failed with exception:");
                exc.printStackTrace();
            }
        };
        channel.write(buffer, 0, "Async Task", handler);
        channel.close();
    }

    public static void writeTransaction() {
        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.APPEND);
        try {
            // writeFile(transactionRes, pathTransaction, true);
            writeFileChannel(pathTransaction, transactionRes, options);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writePayment() {
        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        try {
            writeFile(paymentRes, pathPayment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDeposit() {
        try {
            writeFile(depositRes, pathDeposit);
            depositRes.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
