package View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class BalanceView {
    Path path = Paths.get("data/Balance.txt");
    public void write(String depositNumber, double balance){
        String tab="\t";
        String newLine = System.getProperty("line.separator");
        ArrayList<String> question =new ArrayList<String>(10);
        try {
            Files.write(path,newLine.getBytes(), StandardOpenOption.APPEND );
            Files.write(path,(depositNumber+tab+balance).getBytes(), StandardOpenOption.APPEND );

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
