package sevices;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class WriteToFileService {
    static Path pathTransaction = Paths.get("data/Transaction.txt");
    static Path pathPayment = Paths.get("data/Payment.txt");
    static Path pathDeposit = Paths.get("data/Deposit.txt");
    static Path pathPosition = Paths.get("data/position.txt");
    static String newLine = System.getProperty("line.separator");
    static FileChannel transactionFileChannel;
    static FileChannel depositFileChannel;
    static FileChannel paymentFileChannel;
    static FileChannel positionFileChannel;


    public static void closeChannels() {
        try {
            transactionFileChannel.close();
            depositFileChannel.close();
            positionFileChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void writeFileChannelLine(String input, FileChannel fileChannel) throws IOException {

        input = input + newLine;
        byte[] byteArray = input.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        fileChannel.write(buffer);

    }


    public static void updateFileChannelLine(String input, long position) throws IOException {

        byte[] byteArray = input.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        depositFileChannel.write(buffer, position);


    }

    public static long getPosition() throws IOException {
        return depositFileChannel.position();
    }


    public static void openDepositChannel() throws IOException {

        depositFileChannel = FileChannel.open(pathDeposit, StandardOpenOption.APPEND, StandardOpenOption.CREATE);


    }

    public static void openPaymentChannel() throws IOException {

        paymentFileChannel = FileChannel.open(pathPayment, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

    }

    public static void openPositionChannel() throws IOException {

        positionFileChannel = FileChannel.open(pathPosition, StandardOpenOption.APPEND, StandardOpenOption.CREATE);

    }

    public static void openTransactionChannel() throws IOException {

        transactionFileChannel = FileChannel.open(pathTransaction, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }


}
