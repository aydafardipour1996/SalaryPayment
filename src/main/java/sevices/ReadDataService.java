package sevices;

import exceptions.NoDebtorFoundException;
import model.DepositModel;
import model.PaymentModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

public class ReadDataService {

    static private final List<DepositModel> depositModels = new ArrayList<>();
    static private final List<PaymentModel> paymentModels = new ArrayList<>();
    static Path pathPayment = Paths.get("Payment.txt");
    static Path pathDeposit = Paths.get("Deposit.txt");
    static Path pathPosition = Paths.get("position.txt");

    public static boolean paymentFileExists() {

        return Files.exists(pathPayment);

    }

    public static void removePayments() throws IOException {

        Files.delete(pathPayment);

    }

    public String[] readFile(Path path) throws IOException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate((int) Files.size(path));
        Future<Integer> result = channel.read(buffer, 0);
        while (!result.isDone()) {
        }

        buffer.flip();

        byte[] b = new byte[buffer.remaining()];
        buffer.get(b);
        String res = new String(b, StandardCharsets.UTF_8);
        String[] line = res.split("\\n");

        buffer.clear();
        channel.close();

        return line;

    }

    public List<DepositModel> addDepositData() {
        String[] line = new String[0];
        String[] position = new String[0];
        try {
            line = readFile(pathDeposit);
            position = readFile(pathPosition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < line.length; i++) {

            String[] stringArray = line[i].split("\\t");
            String[] pos = position[i].split("\\t");


            depositModels.add(new DepositModel(stringArray[0], new BigDecimal(stringArray[1].replaceAll("\\s+", "")), Long.parseLong(pos[1].replaceAll("\\s+", ""))));

        }

        return depositModels;

    }


    public List<PaymentModel> addPaymentData() {

        String[] line = new String[0];
        try {

            line = readFile(pathPayment);

        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean isDebtor;
        for (String s : line) {

            isDebtor = false;
            String[] stringArray = s.split("\\t");

            if (stringArray[0].equals("debtor")) {

                isDebtor = true;

            }

            paymentModels.add(new PaymentModel(isDebtor, stringArray[1], new BigDecimal(stringArray[2].replaceAll("\\s+", ""))));

        }

        return paymentModels;

    }

    public BigDecimal getDebtorDeposit(String debtorNumber) throws NoDebtorFoundException {

        boolean found = false;
        BigDecimal Deposit = new BigDecimal(0);

        for (DepositModel depositModel : depositModels) {

            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                Deposit = depositModel.getDeposit();
                found = true;

            }

        }
        if (!found) {

            throw new NoDebtorFoundException("Debtor not found!!");
        }

        return Deposit;

    }

    public boolean depositFileExists() {

        return Files.exists(pathDeposit);

    }

}
