package models;

import java.util.Date;

public class Order {
    private String id;
    private String customerId;
    private String storeId;
    private double price;
    private int quantity;
    private Date createdAt;

    private Order(Builder builder) {
        customerId = builder.customerId;
        quantity = builder.quantity;
        price = builder.price;
        createdAt = builder.createdAt;
        storeId = builder.storeId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Order copy) {
        Builder builder = new Builder();
        builder.quantity = copy.quantity;
        builder.price = copy.price;
        builder.customerId = copy.customerId;
        builder.storeId = copy.storeId;
        builder.createdAt = copy.createdAt;

        return builder;
    }

    public String getCustomerId(){ return customerId; }

    public String getStoreId(){ return storeId; }

    public double getPrice(){ return price; }

    public double getQuantity(){ return quantity; }

    public Date getCreatedAt(){ return createdAt; }

    public static final class Builder {
        private String id;
        private String customerId;
        private String storeId;
        private double price;
        private int quantity;
        private Date createdAt;

        private Builder(){
        }

        public Builder customerId(String customerId ){
            this.customerId = customerId;
            return this;
        }

        public Builder storeId(String storeId ){
            this.storeId = storeId;
            return this;
        }

        public Builder price(double price ){
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity ){
            this.quantity = quantity;
            return this;
        }

        public Builder createdAt(Date createdAt ){
            this.createdAt = createdAt;
            return this;
        }

        public Order build(){ return new Order(this); }
    }
}
