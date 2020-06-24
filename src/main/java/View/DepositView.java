package View;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DepositView {
    static Path path = Paths.get("data/Deposit.txt");
    private static ArrayList<String> res = new ArrayList<String>();
    String tab = "\t";

    public static void write() {
        try {
            Files.write(path, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String depositNumber, BigDecimal amount) {

        res.add(depositNumber + tab + amount);
    }
}
