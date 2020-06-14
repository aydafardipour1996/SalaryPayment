import controller.BalanceController;
import model.BalanceModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
BalanceController balanceController;
    public static void main(String[] args) throws IOException {

//write();
        read();
    }
private static void write() throws IOException {
/*    Path path = Paths.get("src/main/resources/question.txt");

    ArrayList<String> question =new ArrayList<String>(10);
    for (int i=0; i<=10;i++){

    question.add(i, String.valueOf(i * 3));

    }


    Iterable<String> iterable = question;
    Files.write(path,iterable );*/
}
/*private static BalanceModel readBalance(String number){
    Path balancePath = Paths.get("data/Balance.txt");
    try {

        Files.lines(balancePath)
                .filter(line -> line.startsWith(number)).forEach(line -> {
       String s=line;
        });



    } catch (IOException ex) {
        ex.printStackTrace();//handle exception here
    }
    return
}*/
    private static void read(){
    Path balancePath = Paths.get("data/Balance.txt");
        ArrayList<String[]> res =new ArrayList<String[]>(10);
        ArrayList<BalanceModel> balanceModels= new ArrayList<BalanceModel>(10);
    try {

        Files.lines(balancePath).forEach(line -> res.add(line.split("\\t")));

    } catch (IOException ex) {
        ex.printStackTrace();
    }
/*String[] a = res.get(0);
    String aa= a[0];
    System.out.println(aa);
        String[] a1 = res.get(1);
        String aa1= a1[1];
        System.out.println(aa1);*/
}
}
