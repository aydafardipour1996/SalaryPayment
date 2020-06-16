import View.BalanceView;
import View.PaymentView;
import controller.BalanceController;
import controller.PaymentController;
import model.BalanceModel;
import model.PaymentModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Main {
private static final int NOT_FOUND=0;
private static final int CHARGED=1;
private static final int NOT_CHARGED=2;
    static private ArrayList<String[]> res =new ArrayList<String[]>(10);
  static private ArrayList<BalanceModel> balanceModels= new ArrayList<BalanceModel>(10);

    public static void main(String[] args)  {

 ReadData data = new ReadData();
 data.read();
 data.addData();
 String debtorNumber="100.1.2";
 System.out.println( data.isDebtorCharged(debtorNumber));
 payDebt();
    }
private static void payDebt()  {
/*    PaymentModel paymentModel= new PaymentModel(true,"100.3.5",1000);
    PaymentModel paymentModel1=new PaymentModel(false,"100.2.33",200);*/
    PaymentView paymentView = new PaymentView();
    PaymentController paymentController= new PaymentController(new PaymentModel(true,"100.3.5",1000),paymentView);
    PaymentController paymentController1= new PaymentController(new PaymentModel(false,"100.2.33",200),paymentView);
    paymentController.updatePaymentView();
    paymentController1.updatePaymentView();
/*     BalanceModel balanceModel=new BalanceModel("100.200.22",400);
    BalanceView balanceView=new BalanceView();
    BalanceController balanceController=new BalanceController(balanceModel,balanceView);
    balanceController.updateBalanceView();*/
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

}
