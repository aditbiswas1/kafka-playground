package models;

public class Store {
    private String id;
    private String department;

    public Store(String id, String department){
        this.id=id;
        this.department=department;
    }

    public String getStoreId(){
        return id;
    }
    public String getDepartment(){
        return department;
    }
}
