package sevices;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {
    private Constants() {
    }
    public static final String space = "               ";
    public static final String tab = "\t";
    public static final String newLine = System.getProperty("line.separator");

    public static final Path pathTransaction = Paths.get("Transaction.txt");
    public static final Path pathPayment = Paths.get("Payment.txt");
    public static final Path pathDeposit = Paths.get("Deposit.txt");
    public static final Path pathPosition = Paths.get("position.txt");

}
