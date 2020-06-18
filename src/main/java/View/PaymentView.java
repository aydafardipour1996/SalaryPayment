package View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class PaymentView {
     private static ArrayList<String> res =new ArrayList<String>();
    static Path path = Paths.get("data/Payment.txt");
    String tab="\t";
    String newLine = System.getProperty("line.separator");
    public void writeLine(boolean isDebtor, String depositNumber, double amount){

        try {
            if (isDebtor){
                Files.write(path,("debtor"+tab+depositNumber+tab+amount).getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE );
            }else {
                Files.write(path, ("creditor" + tab + depositNumber + tab + amount).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            }
            Files.write(path,newLine.getBytes(), StandardOpenOption.APPEND,StandardOpenOption.CREATE );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(boolean isDebtor, String depositNumber, double amount){
        if (isDebtor){
           res.add("debtor"+tab+depositNumber+tab+amount);
        }else
           res.add("creditor"+tab+depositNumber+tab+amount);
    }

    public static void write(){
        try {
            Files.write(path,res, StandardOpenOption.APPEND,StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
