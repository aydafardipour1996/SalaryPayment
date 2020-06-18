import model.BalanceModel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class ReadData {
    private static final int NOT_FOUND=0;
    private static final int CHARGED=1;
    private static final int NOT_CHARGED=2;
    static private ArrayList<String[]> res =new ArrayList<>(10);
    static private ArrayList<BalanceModel> balanceModels= new ArrayList<BalanceModel>(10);
    public  void read(){
        Path balancePath = Paths.get("data/Balance.txt");

        try {

            Files.lines(balancePath).forEach(line -> res.add(line.split("\\t")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
    public ArrayList<BalanceModel>  addData(){
        for (String[] line : res) {
            balanceModels.add(new BalanceModel(line[0], Double.parseDouble(line[1])));
        }
return balanceModels;
    }
    public  int isDebtorCharged(String debtorNumber){
        int state=NOT_FOUND;

        for (BalanceModel balanceModel : balanceModels) {
            if (Objects.equals(balanceModel.getDepositNumber(), debtorNumber)) {

                if (balanceModel.getBalance() > 0) {
                    state=CHARGED;
                } else {
                    state=NOT_CHARGED;
                }
            }

        }
        if(state==NOT_FOUND)
            System.out.println("Debtor not found!!");
        return state ;
    }
    public double getDebtorBalance(String debtorNumber){
        double balance=-1;
        for (BalanceModel balanceModel : balanceModels) {
            if (Objects.equals(balanceModel.getDepositNumber(), debtorNumber)) {

       balance=balanceModel.getBalance();
            }

        }
        return balance;
    }

    
}
