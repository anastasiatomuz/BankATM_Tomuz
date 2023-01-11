public class Customer {
    private String name;
    private int PIN;
    private double savingsAccountAmount;
    private double checkingAccountAmount;

    public Customer(String name, int PIN){
        this.name = name;
        this.PIN = PIN;
        this.savingsAccountAmount = 0;
        this.checkingAccountAmount = 0;
    }

    /**
     *
     * precondition: amount is a valid amount (a multiple of 5)
     * @param accountType =
     * @param amount
     * @return True if transaction was successful
     */
    public boolean withdraw(int num, double amount){
        //check which account to edit
        double accountAmount = determineAccountType(accountType);
        //checks if user has enough money in their account
        if (!enoughMoney(accountAmount, amount)) {
            return false;
        }
        //check different ways to split up between 20 and 5 dollar bills
        int
        //subtract money from account

        //return true
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
