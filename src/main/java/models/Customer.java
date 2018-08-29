package models;

public class Customer {
    private String firstName;
    private String lastName;
    private String id;

    public Customer(String firstName, String lastName, String id){
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }
    public String getCustomerId(){
        return id;
    }
    public String getCustomerFirstName(){
        return firstName;
    }
};
