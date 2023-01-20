import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class ATM {
    /*
    1. welcome user
    2. create customer (entering their name and choosing a PIN)
    3. customer is assigned two accounts
        - savings account ($0)
        - checking account ($0)

    Users now have access to their account

    4. User prompted to enter PIN
    5. Menu
        - Withdraw money
            * The ATM can give out $5 and $20 bills, so the customer must enter an amount to withdraw
              that can be distributed using only these bills; the customer can choose how many of each
              bill to receive (for example, if they withdraw $40, they can choose two 20’s, eight 5’s,
              or one 20 and four 5’s).
            * The customer should not be allowed to enter an invalid amount (i.e. one that is not a
              multiple of 5, e.g. $17).
            * If the customer attempts to withdraw more money than is in the account, an error message
               should occur (“insufficient funds!”)

        - Deposit money
        - Transfer money between accounts
        - Get account balances
        - Change PIN
        - Exit

    6. After any action, have a confirmation (“receipt”) shown on the screen
    7. User asked if they want to do anything else
         -yes: ask for PIN again; return to Main menu
         -no: thank the user for visiting
     */

    private Scanner scan;
    private Account savings;
    private Account checking;
    private int transactionID;
    public ATM(){
        this.scan = new Scanner(System.in);
        this.savings = new Account("Savings", 0);
        this.checking = new Account("Checking", 0);
        this.transactionID = 10000;
    }
    public void start() {
        System.out.println("Welcome to the ATM!");
        System.out.println("To use the ATM, you must create an account.\n");
        System.out.print("Please enter your full name: ");
        String userName = scan.nextLine();
        clearScreen();
        System.out.print("Please enter a four digit PIN you will wish to use to log into your account: ");
        int PIN = scan.nextInt();
        scan.nextLine();
        while (numDigits(PIN) != 4){
            System.out.println("PIN doesn't match criteria.");
            System.out.println("PIN must be 4 digit long and cannot have leading zeros (ie 0021).");
            System.out.print("Enter a new PIN: ");
            PIN = scan.nextInt();
            scan.nextLine();
        }
        Customer customer = new Customer(userName, PIN);
        Account savings = new Account("Savings account", 0);
        Account checking = new Account("Checking account", 0);
        System.out.println("Two new accounts have been created");
        clearScreen();
        boolean open = true;
        while (open)
        {
            clearScreen();
            System.out.print("Please enter your PIN: ");
            int userPIN = scan.nextInt();
            scan.nextLine();
            while (userPIN != customer.getPIN())
            {
                System.out.println("The PIN you have entered doesn't match our records");
                System.out.print("Please enter your PIN: ");
                userPIN = scan.nextInt();
                scan.nextLine();
            }
            open = menu(customer);
            System.out.print("Do you want to do anything else? (y/n) ");
            String continueState = scan.nextLine();

            if (continueState.equals("y") || continueState.equals("Y"))
            {
                open = true;
            }
            else if (continueState.equals("N") || continueState.equals("n"))
            {
                open = false;
            }
            else {
                System.out.println("Invalid answer");
            }
        }
        System.out.println("Thank you for visiting. Hope to see you soon.");

    }

    public boolean menu(Customer customer){
        String receiptMessage = "";
        System.out.println("Here is a list of available actions: ");
        System.out.println("1. Make a withdrawal");
        System.out.println("2. Make a deposit");
        System.out.println("3. Transfer money between accounts");
        System.out.println("4. Get account balances");
        System.out.println("5. Change PIN");
        System.out.println("6. Exit");

        System.out.print("What do you wish to do? ");
        int userChoice = scan.nextInt();
        scan.nextLine();

        //make a withdrawal
        if (userChoice == 1)
        {
            Account accountType = getAccountType("What account do you want to make a  withdrawal from? ");
            System.out.print("How much do you want to withdraw? $");
            int moneyToWithdraw = scan.nextInt();
            scan.nextLine();
            while (moneyToWithdraw % 5 != 0)
            {
                System.out.println("This ATM currently only dispenses bills of $5 and $20");
                System.out.println("Amount to withdraw must be a multiple of 5");
                System.out.print("How much do you want to withdraw? $");
                moneyToWithdraw = scan.nextInt();
                scan.nextLine();
            }
            if (enoughMoney(accountType, moneyToWithdraw))
            {
                accountType.withdraw(moneyToWithdraw);
                determineBillQuantities(moneyToWithdraw);
                transactionID ++;
                receiptMessage += "Transaction ID: " + transactionID + "\n$" + moneyToWithdraw + " withdrawn successfully";
            }
            else
            {
                receiptMessage += "Cannot complete transfer. Insufficient balance in " + accountType.getName() + " account";
            }
            receiptMessage += "\n" + obtainAccountBalance();
        }

        //make a deposit
        else if (userChoice == 2)
        {
            Account accountType = getAccountType("What account do you want to make a deposit to? ");
            System.out.print("How much do you wish to deposit? $");
            double amountToDeposit = scan.nextDouble();
            scan.nextLine();
            accountType.deposit(amountToDeposit);
            transactionID ++;
            receiptMessage += "Transaction ID: " + transactionID + "\n$" + amountToDeposit + " deposited successfully";
            receiptMessage += "\n" + obtainAccountBalance();
        }

        //complete a transfer
        else if (userChoice == 3)
        {
            Account fromAccount = getAccountType("What account do you want to transfer from?");
            while (fromAccount == null)
            {
                System.out.println("Invalid choice");
                fromAccount = getAccountType("What account do you want to transfer from?");
            }
            Account toAccount;
            if (fromAccount == savings)
            {
                toAccount = checking;
            }
            else
            {
                toAccount = savings;
            }

            System.out.print("How much do you want to transfer? $");
            double amountToTransfer = scan.nextDouble();
            scan.nextLine();

            if (enoughMoney(fromAccount, amountToTransfer))
            {
                fromAccount.withdraw(amountToTransfer);
                toAccount.deposit(amountToTransfer);
                transactionID ++;
                receiptMessage += "Transaction ID: " + transactionID + "\nTransferred $" + amountToTransfer + " from "
                        + fromAccount.getName() + " to " + toAccount.getName();
            }
            else
            {
                receiptMessage += "Cannot complete transfer. Insufficient balance in " + fromAccount.getName() + " account";
            }
            receiptMessage += "\n" + obtainAccountBalance();
        }

        else if (userChoice == 4)
        {
            receiptMessage += obtainAccountBalance();
        }

        //change PIN
        else if (userChoice == 5)
        {
            System.out.print("Enter your new PIN: ");
            int newPIN = scan.nextInt();
            scan.nextLine();
            while (numDigits(newPIN) != 4){
                System.out.println("PIN doesn't match criteria.");
                System.out.println("PIN must be 4 digit long.");
                System.out.print("Enter a new PIN: ");
                newPIN = scan.nextInt();
                scan.nextLine();
            }
            customer.setPIN(newPIN);
            receiptMessage += "PIN changed successfully";
        }

        //exit
        else if (userChoice == 6)
        {
            printReceipt(customer.getName(), "You chose to exit");
            return false;
        }

        else
        {
            System.out.println("Invalid choice");
        }

        printReceipt(customer.getName(), receiptMessage);
        return true;
    }

    private boolean enoughMoney(Account accountName, double moneyToRemove)
    {
        return accountName.getBalance() - moneyToRemove >= 0;
    }

    private String obtainAccountBalance()
    {
        return "Savings Account: $" + savings.getBalance() + "\nChecking Account: $" + checking.getBalance();
    }

    private Account getAccountType(String message)
    {
        Account name;
        System.out.println("1. Savings account");
        System.out.println("2. Checking account");
        System.out.print(message);
        int accountFromChoice = scan.nextInt();
        scan.nextLine();
        while (accountFromChoice != 1 && accountFromChoice != 2){
            System.out.println("\nInvalid choice");
            System.out.println("1. Savings account");
            System.out.println("2. Checking account");
            System.out.print(message);
            accountFromChoice = scan.nextInt();
            scan.nextLine();
        }
        if (accountFromChoice == 1)
        {
            name = savings;
        }
        else if (accountFromChoice == 2)
        {
            name = checking;
        }
        else {
            name = null;
        }
        return name;
    }

    private int numDigits(int numToCheck)
    {
        return (int) (Math.log10(numToCheck) + 1);
    }

    private void determineBillQuantities(int amount){
        Map<Integer, Integer[]> table = new HashMap<>();
        int amtTemp = amount;
        int methodNum = 0;
        while (amtTemp >= 20.0){
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
        System.out.println("Combination     # of $20 bills     # of $5 bills");
        for (int i = 1; i <= table.size(); i ++){
            int val1;
            int val2;
            System.out.print("     " + i + "                 ");
            Integer[] theArray = table.get(i);
            val1 = theArray[0];
            val2 = theArray[1];
            System.out.println(val1 + "                 " + val2);
        }
        System.out.print("\nEnter the combination of $20 and $5 you would like to receive: ");
        int combination = scan.nextInt();
        scan.nextLine();
        System.out.println(table.get(combination)[0] + " of $20 bills dispensed and " + table.get(combination)[1] + " of $5 bills dispensed");
    }

    private void printReceipt(String customerName, String message)
    {
        System.out.println("**************************");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Time: " + timestamp);
        System.out.println("Customer: " + customerName);
        System.out.println();
        System.out.println(message);
        System.out.println("**************************");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
