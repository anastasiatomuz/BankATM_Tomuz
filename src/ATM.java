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
    public ATM(){
        this.scan = new Scanner(System.in);
    }
    public void start() {
        System.out.println("Welcome to the ATM!");
        System.out.println("To use the ATM, you must create an account.\n");
        System.out.print("Please enter your full name: ");
        String userName = scan.nextLine();
        System.out.print("Please enter a four digit PIN you will wish to use to log into your account: ");
        int PIN = scan.nextInt();
        while (PIN < 1000 || PIN > 9999){
            System.out.println("PIN doesn't match criteria.");
            System.out.println("PIN must be 4 digit long.");
            System.out.print("Enter a new PIN: ");
            PIN = scan.nextInt();
        }
        Customer customer = new Customer(userName, PIN);
        Account savings = new Account("Savings account", 0);
        Account checking = new Account("Checking account", 0);
        System.out.println("Two new accounts have been created");
        System.out.println(customer.obtainAccountBalance());
        menu();
    }

    public void menu(){
        boolean open = true;
        while (open) {
            System.out.println("Here is a list of available actions: ");
            System.out.println("1. Make a withdrawal");
            System.out.println("2. Make a deposit");
            System.out.println("3. Transfer money between accounts");
            System.out.println("4. Get account balances");
            System.out.println("5. Change PIN");
            System.out.println("6. Exit");

            int userChoice = scan.nextInt();

            if (userChoice == 3){
                System.out.println("1. Savings account");
                System.out.println("2. Checking account");
                System.out.print("Which account do you want to transfer from? ");
                int accountFromChoice = scan.nextInt();
                if (accountFromChoice == 1){

                }
            }
        }
    }
}
