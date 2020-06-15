import controller.BalanceController;
import model.BalanceModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {

    static private ArrayList<String[]> res =new ArrayList<String[]>(10);
  static private ArrayList<BalanceModel> balanceModels= new ArrayList<BalanceModel>(10);

    public static void main(String[] args)  {

//write();
        read();
        addData();
        System.out.println(isDebtorCharged());
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
private static void addData(){
    for (int i=0; i<res.size();i++) {
        String[] line = res.get(i);
        balanceModels.add(new BalanceModel(line[0], Double.parseDouble(line[1])));
    }
   System.out.println(balanceModels.get(1).getBalance());
}
public static Boolean isDebtorCharged(){
        boolean isCharged=false;
for (int i=0; i<balanceModels.size();i++){
   if(Objects.equals(balanceModels.get(i).getDepositNumber(), "100.1.2")){
     if (balanceModels.get(i).getBalance()>0) {
         isCharged=true;
     }
   }
}
        return isCharged;
}
}
