package sevices;

import exceptions.NoDebtorFoundException;
import model.DepositModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ReadData {
    static Path pathTransaction = Paths.get("data/Transaction.txt");
    static Path pathPayment = Paths.get("data/Payment.txt");
    static Path pathDeposit = Paths.get("data/Deposit.txt");
    private static final BigDecimal ZERO = new BigDecimal(0);
    static private List<DepositModel> depositModels = new ArrayList<>();

    public String[] readFile(Path path) throws IOException, InterruptedException, ExecutionException {
        //   Path path = Paths.get("data/Deposit.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(400);
        Future<Integer> result = channel.read(buffer, 0); // position = 0
        while (!result.isDone()) {
            System.out.println("Task of reading file is in progress asynchronously.");
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
        String[] line= new String[0];
        try {
            line = readFile(pathDeposit);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < line.length; i++) {
            String[] stringArray = line[i].split("\\t");
            depositModels.add(new DepositModel(stringArray[0], new BigDecimal(stringArray[1].replaceAll("\\s+", ""))));


        }

        return depositModels;

    }

/*    public void read() {
        Path DepositPath = Paths.get("data/Deposit.txt");

        try {

            Files.lines(DepositPath).forEach(line -> res.add(line.split("\\t")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }*/



    public BigDecimal getDebtorDeposit(String debtorNumber) throws NoDebtorFoundException {
        boolean found = false;
        BigDecimal Deposit = new BigDecimal(0);
        for (DepositModel depositModel : depositModels) {
            if (Objects.equals(depositModel.getDepositNumber(), debtorNumber)) {

                Deposit = depositModel.getAmount();
                found = true;
            }

        }
        if (!found) {
            throw new NoDebtorFoundException("Debtor not found!!");
        }
        return Deposit;
    }


}
