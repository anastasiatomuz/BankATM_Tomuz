public class Account {
    private String name;
    private double balance;

    public Account(String name, double balance){
        this.name = name;
        this.balance = balance;
    }

    public void deposit(double moneyToAdd){
        balance += moneyToAdd;
    }

    public void withdraw(double moneyToWithdraw){
        balance -= moneyToWithdraw;
    }

    public String getName()
    {
        return name;
    }

    public double getBalance(){
        return balance;
    }
}
