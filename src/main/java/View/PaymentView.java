package View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class PaymentView {
    Path path = Paths.get("data/Payment.txt");
    public void write(boolean isDebtor, String depositNumber, double amount){
        String tab="\t";
        String newLine = System.getProperty("line.separator");
        try {
            Files.write(path,newLine.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE );
            if (isDebtor){
                Files.write(path,("debtor"+tab+depositNumber+tab+amount).getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE );
            }else
            Files.write(path,("creditor"+tab+depositNumber+tab+amount).getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE );

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
