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
        System.out.println("To use the ATM, you must create an account.");
        System.out.print("Please enter your full name: ");
        String userName = scan.nextLine();
        int PIN = scan.nextInt();
    }
}
