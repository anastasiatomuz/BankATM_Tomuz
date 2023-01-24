import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/*
Program outline

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

public class ATM {
    private Scanner scan;
    private Account savings;
    private Account checking;
    private int transactionID;
    private Customer customer;

    public ATM(){
        this.scan = new Scanner(System.in);
        this.transactionID = 10000; //beginning transaction ID that increases as number of transactions increase
    }

    /**
     * The central method that starts the program (the ATM)
     *
     * finds user's name and PIN
     *
     * Creates Customer object and Account objects
     * References menu helper method that displays and carries out available functions of the ATM
     * When user is done using the ATM, program prints goodbye message
     */
    public void start() {
        //
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
        //creates customer, and two account objects
        customer = new Customer(userName, PIN);
        savings = new Account("Savings account", 0);
        checking = new Account("Checking account", 0);
        System.out.println("Two new accounts have been created");
        clearScreen();

        //runs the main program while the user chooses not to exit and chooses to continue on
        boolean open = true;
        while (open)
        {
            //asks user to enter PIN each time they perform an action (accesses the main menu)
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
            open = menu();
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


    /**
     * helper method accessed in start method that lists out the menu option and asks user which option they want
     * to choose
     *
     * uses if else statements to which option user chooses and calls appropriate methods
     *
     * @return true if the user wishes to continue using the ATM, false if they want to exit
     */
    private boolean menu(){
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
            //asks user info like what account they want to withdraw from and how much they want to withdraw
            Account accountType = getAccountType("What account do you want to make a  withdrawal from? ");
            System.out.print("How much do you want to withdraw? $");
            int moneyToWithdraw = scan.nextInt();
            scan.nextLine();
            while (moneyToWithdraw % 5 != 0) //checks if the amount user wants to withdraw is a multiple of 5
            {
                System.out.println("This ATM currently only dispenses bills of $5 and $20");
                System.out.println("Amount to withdraw must be a multiple of 5");
                System.out.print("How much do you want to withdraw? $");
                moneyToWithdraw = scan.nextInt();
                scan.nextLine();
            }
            /*if all conditions are acceptable, withdraws money from account and lists combination of
            5 and 20 dollar bills
            */
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
        /*
        asks which account user wants to make a deposit to and how much they want to transfer
        deposits money and creates appropriate message for receipt
         */
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
        /*
        asks user which account they want to transfer from and how much they want to transfer
        if there's enough money to complete the transfer, the money is transferred and a message for the receipt is created
         */
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

        //sets the receipt to print the balance available in the two accounts
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
        //returns false to indicate that the user doesn't wish to continue using the ATM
        else if (userChoice == 6)
        {
            printReceipt("You chose to exit");
            return false;
        }

        else
        {
            System.out.println("Invalid choice");
        }

        //a receipt is displayed at the end of each action
        printReceipt(receiptMessage);
        return true; //to indicate user's wish to continue using ATM
    }

    /**
     * helper method that determines if an action should be carried out so that the user won't be in debt afterwards
     *
     * @param accountName Account object indicates which account user wants to withdraw from
     * @param moneyToRemove the "amount indicated" to remove
     * @return a boolean if the user has enough money in their account
     *         true: by removing the amount indicated, user would have $0, or more in their account
     *         false: by removing the amount indicated, user would have less than $0 in their account (they would be in debt)
     */
    private boolean enoughMoney(Account accountName, double moneyToRemove)
    {
        return accountName.getBalance() - moneyToRemove >= 0;
    }

    /**
     * returns a String literal with current balances in the two accounts
     *
     * used for printing receipts that announces user how much they have in their accounts
     * and when the user chooses to check their account balances
     */
    private String obtainAccountBalance()
    {
        return "Savings Account: $" + savings.getBalance() + "\nChecking Account: $" + checking.getBalance();
    }

    /**
     * Determines the Account object the user wants to perform action on
     *
     * @param message used for printing a message appropriate when the account type needs to be accessed
     * @return Account object that the user wants to perform an action on
     */
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
            name = null; //returns null if the user chooses an "invalid choice"
        }
        return name;
    }

    /**
     * determines the number of digits in an integer
     * helpful for creating a 4-digit PIN
     *
     * @param numToCheck the integer that we want to determine the number of digits it contains
     * @return the number of digits in the parameter number
     */
    private int numDigits(int numToCheck)
    {
        return (int) (Math.log10(numToCheck) + 1);
    }

    /**
     * finds the "combination" of 5 and 20 dollar bills that can be made given an amount
     * precondition: amount must be a multiple of 5
     * @param amount the dollar amount that needs to find the combination of 5 and 20 dollar bills
     */
    private void determineBillQuantities(int amount){
        /*
        Use of hashmap to store key as "combination number" and value as an integer list with two values:
        number of 20 dollar bills (index 0) and number of 5 dollar bills (index 1).

        Iterates through the possible values that can be made with $20 bills until the combination can't be made
        with a 20.
        */

        //credits for inspiration: https://stackoverflow.com/questions/4126272/how-do-i-implement-nested-arraylist
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

        //"iterates" through a hashmap by combination counter to print the table of all possible combinations
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
        //lets the user choose which combination they want to withdraw
        System.out.print("\nEnter the combination of $20 and $5 you would like to receive: ");
        int combination = scan.nextInt();
        scan.nextLine();
        System.out.println(table.get(combination)[0] + " of $20 bills dispensed and " + table.get(combination)[1] + " of $5 bills dispensed");
    }

    /**
     * Prints a receipt of what action the user has just completed
     * @param message String literal whether action was completed successfully, or unsuccessfully
     */
    private void printReceipt(String message)
    {
        System.out.println("**************************");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Time: " + timestamp);
        System.out.println("Customer: " + customer.getName());
        System.out.println();
        System.out.println(message);
        System.out.println("**************************");
    }

    /**
     * helps clear the print statements so the console is appealing to the eye
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
