public class Customer {
    //instance methods
    private String name;
    private int PIN;

    //constructor with name and PIN as instance variables
    public Customer(String name, int PIN){
        this.name = name;
        this.PIN = PIN;

    }

    //getter method for PIN
    public int getPIN()
    {
        return PIN;
    }

    //setter method for PIN
    public void setPIN(int newPIN)
    {
        PIN = newPIN;
    }

    //getter method for the name of the customer
    public String getName(){
        return name;
    }

}