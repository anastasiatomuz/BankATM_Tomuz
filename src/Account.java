public class Account {
    //instance variables
    private String name;
    private double balance;

    //constructor with name and balance variables
    public Account(String name, double balance){
        this.name = name;
        this.balance = balance;
    }

    //getter method for the name of the Account
    public String getName()
    {
        return name;
    }

    //getter method for the balance
    public double getBalance(){
        return balance;
    }


    //method that adds an amount (parameter: moneyToAdd) to the balance of the account
    public void deposit(double moneyToAdd){
        balance += moneyToAdd;
    }

    //method that withdraws (removes) an amount (moneyToWithdraw) from the balance
    public void withdraw(double moneyToWithdraw){
        balance -= moneyToWithdraw;
    }

}
