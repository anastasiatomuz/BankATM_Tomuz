import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class Customer {
    private String name;
    private int PIN;
    private double savingsAccountAmount;
    private double checkingAccountAmount;

    public Customer(String name, int PIN){
        this.name = name;
        this.PIN = PIN;
        this.savingsAccountAmount = 50;
        this.checkingAccountAmount = 50;
    }

    /**
     *
     * precondition: amount is a valid amount (a multiple of 5)
     * @param accountType = 0 for savings account; 1 for checking account
     * @param amount = the amount of money user wants to withdraw (should be a multiple of 5)
     * @return True if transaction was successful
     */
    public boolean withdraw(int accountType, int amount){
        //distinguish the account from which the user wants to make a withdraw
        double accountAmount;
        if (accountType == 0){
            accountAmount = savingsAccountAmount;
        } else  {
            accountAmount = checkingAccountAmount;
        }

        //checks if user has enough money in their account
        if (!enoughMoney(accountAmount, amount)) {
            return false;
        }

        //check different ways to split up between 20 and 5 dollar bills
        //first value of hashmap to implement num of 20's used, 2nd value = # of 5's used
        Map<Integer, Integer[]> table = new HashMap<>();
//        ArrayList<ArrayList> solutions = new ArrayList<Integer>();
        int amtTemp = amount;
        int methodNum = 0;
        while (amtTemp / 20.0 > 0){
            int numOfTwenty = amtTemp / 20;
            int subtracted = amount - numOfTwenty * 20;
            int numOfFive = subtracted / 5;
            methodNum ++;
            Integer[] solution = {numOfTwenty, numOfFive};
            table.put(methodNum, solution);
            amtTemp -= 20;
        }
        methodNum ++;
        int fivesUsed = amount / 5;
        Integer[] toAdd = {0, fivesUsed};
        table.put(methodNum, toAdd);

        for (int i = 1; i < table.size(); i ++){
            int val1;
            int val2;
            System.out.print(i + ": ");
            Integer[] theArray = table.get(i);
            val1 = theArray[0];
            val2 = theArray[1];
            System.out.println(val1 + ", " + val2);
        }



        //subtract money from account
        if (accountType == 0){
            savingsAccountAmount -= amount;
        } else {
            checkingAccountAmount -= amount;
        }
        return true; //operations performed successfully
    }

    public void deposit(String accountType, double amount){
        //checks which account
        //adds money
    }

    public boolean transfer(String fromAccount, String toAccount){
        //checks if from account has enough money to withdraw
            //if not return false
        //subtract from FROMACCOUNT and add to TOACCOUNT
        //return true
        return true;
    }

    public String obtainAccountBalance(){
        return "Savings Account: $" + savingsAccountAmount + "\nChecking Account: $" + checkingAccountAmount;
    }

    //helper methods
    public double determineAccountType(String accountType){
        if (accountType.equals("savings")){
            return savingsAccountAmount;
        } else if (accountType.equals("checking")){
            return checkingAccountAmount;
        } else {
            return -1;
        }
    }

   public boolean enoughMoney(double account, double amount){
        return account - amount < 0;
   }
}


/*
https://stackoverflow.com/questions/4126272/how-do-i-implement-nested-arraylist
 */