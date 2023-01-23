import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
public class Customer {
    private String name;
    private int PIN;


    public Customer(String name, int PIN){
        this.name = name;
        this.PIN = PIN;

    }

    public int getPIN()
    {
        return PIN;
    }

    public void setPIN(int newPIN)
    {
        PIN = newPIN;
    }

    public String getName(){
        return name;
    }

}


/*
https://stackoverflow.com/questions/4126272/how-do-i-implement-nested-arraylist
*/