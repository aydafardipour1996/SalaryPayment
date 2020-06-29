import Exceptions.NoDebtorFoundException;
import model.DepositModel;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ReadData {
    private static final BigDecimal ZERO = new BigDecimal(0);
    static private List<String[]> res = new ArrayList<>(10);
    static private List<DepositModel> depositModels = new ArrayList<>();

    public void readFile() throws IOException, InterruptedException, ExecutionException {
        Path path = Paths.get("data/Deposit.txt");
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        ByteBuffer buffer = ByteBuffer.allocate(400);
        Future<Integer> result = channel.read(buffer, 0); // position = 0
        while (!result.isDone()) {
            System.out.println("Task of reading file is in progress asynchronously.");
        }

        buffer.flip();

        byte[] b = new byte[buffer.remaining()];
        buffer.get(b);
        String a = new String(b, StandardCharsets.UTF_8);


        String[] line = a.split("\\n");
        for (int i = 0; i < line.length; i++) {
            res.add(line[i].split("\\t"));
        }


        buffer.clear();
        channel.close();
    }

    public void read() {
        Path DepositPath = Paths.get("data/Deposit.txt");

        try {

            Files.lines(DepositPath).forEach(line -> res.add(line.split("\\t")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public List<DepositModel> addData() {
        for (String[] l : res) {
            depositModels.add(new DepositModel(l[0], new BigDecimal(l[1].replaceAll("\\s+", ""))));
        }
        return depositModels;
    }


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
