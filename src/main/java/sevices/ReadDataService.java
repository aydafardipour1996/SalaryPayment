package sevices;

import model.DepositModel;
import model.PaymentModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import static sevices.Constants.*;

public class ReadDataService {

    static private final List<DepositModel> depositModels = new ArrayList<>();
    static private final List<PaymentModel> paymentModels = new ArrayList<>();

    public static boolean paymentFileExists() {

        return Files.exists(pathPayment);

    }

    public static void removePayments() throws IOException {

        Files.delete(pathPayment);

    }

    public static BigDecimal readDeposit(String depositNumber) throws IOException {

        Optional<String> stringOptional = Files.lines(pathDeposit)
                .filter(l -> l.startsWith(depositNumber))
                .findFirst();

        if (stringOptional.isPresent()) {

            String[] line = stringOptional.get().split("\\t");

            return new BigDecimal(line[1].replaceAll("\\s+", ""));
        } else if (depositNumber.equals(PaymentService.debtorNumber)) {
            return new BigDecimal(-1);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static long readPosition(String depositNumber) throws IOException {

        Optional<String> stringOptional = Files.lines(pathPosition)
                .filter(l -> l.startsWith(depositNumber))
                .findFirst();

        if (stringOptional.isPresent()) {

            String[] line = stringOptional.get().split("\\t");

            return Long.parseLong(line[1].replaceAll("\\s+", ""));
        } else {
            return WriteToFileService.getPosition();

        }

    }

    public String[] readFile(Path path) throws IOException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
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


    public List<PaymentModel> getPaymentData() {

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

    public boolean depositFileExists() {

        return Files.exists(pathDeposit);

    }

}
