import View.BalanceView;
import View.PaymentView;
import View.TransactionView;
import controller.BalanceController;
import controller.PaymentController;
import controller.TransactionController;
import model.BalanceModel;
import model.PaymentModel;
import model.TransactionModel;

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

    static private ArrayList<String[]> res =new ArrayList<String[]>();
    static private ArrayList<TransactionModel> transactionModels= new ArrayList<>();
    static private ArrayList<BalanceModel> balanceModels= new ArrayList<>();
    static private double debtorBalance;
    static private double debtorBalanceSum;
    static private final String debtorNumber="100.1.2";
    static PaymentView paymentView = new PaymentView();
    static TransactionView transactionView= new TransactionView();
    public static void main(String[] args)  {

 ReadData data = new ReadData();
 data.read();
 balanceModels=data.addData();

 System.out.println( data.isDebtorCharged(debtorNumber));
 debtorBalance=data.getDebtorBalance(debtorNumber);

 payDept("100.8.8",10);
 payDept("200.0.0",40);
 PaymentController paymentController= new PaymentController(new PaymentModel(true,debtorNumber,debtorBalanceSum),paymentView);
 paymentController.updatePaymentViewLine();
 setBalance();
 update();
    }
private static void setPayment(boolean isDebtor, String depositNumber, double amount)  {

    PaymentModel paymentModel=new PaymentModel(isDebtor,depositNumber,amount);
    PaymentController paymentController= new PaymentController(paymentModel,paymentView);
    paymentController.addDataToView();


    }
private static void update(){
     PaymentController.updatePayment();
     TransactionController.updateTransaction();
     BalanceController.updateBalanceView();
}

private static void setTransaction(String sender,String receiver, double amount){
        TransactionModel transactionModel=new TransactionModel(sender,receiver,amount);
     //   transactionModels.add(transactionModel);
        TransactionController transactionController=new TransactionController(transactionModel,transactionView);
        transactionController.addDataToView();

}
private static void setBalance(){
        BalanceView balanceView=new BalanceView();
    for (BalanceModel balanceModel:balanceModels){
        if (Objects.equals(balanceModel.getDepositNumber(), debtorNumber)){
           balanceModel.setBalance(debtorBalance);

        }
       BalanceController balanceController=new BalanceController(balanceModel,balanceView) ;
       balanceController.addDataToView();
    }
}

    private static void payDept(String creditorNumber, double amount){
        boolean exists=false;
        if (amount <= debtorBalance){
            setPayment(false,creditorNumber,amount);
            setTransaction(debtorNumber,creditorNumber,amount);
            debtorBalance -= amount;
            debtorBalanceSum +=amount;
    for (int i =0 ; i<balanceModels.size(); i++){

      if(Objects.equals(balanceModels.get(i).getDepositNumber(), creditorNumber)){
          balanceModels.get(i).setBalance(balanceModels.get(i).getBalance()+amount);
          exists=true;
      }
    }
    if(!exists) {
        balanceModels.add(new BalanceModel(creditorNumber,amount));
    }

        }else {
            System.out.println("not enough balance!!");
        }

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
