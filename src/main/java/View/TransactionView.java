package View;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class TransactionView {
    static Path path = Paths.get("data/Transaction.txt");
    private static ArrayList<String> res = new ArrayList<String>(10);
    String tab = "\t";
    String newLine = System.getProperty("line.separator");

    public static void write() {
        try {
            Files.write(path, res, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String sender, String receiver, BigDecimal amount) {

        try {
            Files.write(path, newLine.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);

            Files.write(path, (sender + tab + receiver + tab + amount).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String sender, String receiver, BigDecimal amount) {

        res.add(sender + tab + receiver + tab + amount);
    }
}
