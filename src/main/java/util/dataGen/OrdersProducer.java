package util.dataGen;

import com.github.javafaker.Faker;
import models.Customer;
import models.Order;
import models.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class OrdersProducer {

    private static final int UNIQUE_CUSTOMERS = 10;
    private static final int UNIQUE_STORES = 2;


    private static Logger logger = LoggerFactory.getLogger(OrdersProducer.class);
    private static Faker dateFaker = new Faker();
    private static Supplier<Date> timestampGenerator = () -> dateFaker.date().past(15, TimeUnit.MINUTES, new Date());

    public static List<Order> generateOrders(int ordersCount){
        Faker faker = new Faker();
        List<Customer> customers = generateCustomers(UNIQUE_CUSTOMERS);
        List<Store> stores = generateStores(UNIQUE_STORES);
        List <Order> orders = new ArrayList<>(ordersCount);
        Random random = new Random();
        for(int i=0; i< ordersCount; i++){
            int quantity = faker.number().numberBetween(1, 5);
            double price = Double.parseDouble(faker.commerce().price(4.00, 300.0));
            Date createdAt = timestampGenerator.get();

            Customer customer = customers.get(random.nextInt(UNIQUE_CUSTOMERS));
            Store store = stores.get(random.nextInt(UNIQUE_STORES));

            Order order = Order.builder().customerId(customer.getCustomerId())
                            .storeId(store.getStoreId())
                            .price(price)
                            .quantity(quantity)
                            .createdAt(createdAt)
                            .build();
            orders.add(order);
        }
        return orders;
    }

    private static List<Customer> generateCustomers(int customersCount){
        Faker faker = new Faker();
        List<Customer> customers = new ArrayList<>(customersCount);

        for(int i=0; i< customersCount; i++){
            customers.add(new Customer(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.idNumber().valid()));
        }
        return customers;
    }

    private static List<Store> generateStores(int storesCount){
        Faker faker = new Faker();
        List<Store> stores = new ArrayList<>(storesCount);

        for(int i=0; i < storesCount; i++){
            stores.add(new Store(faker.idNumber().valid(),
                                 faker.commerce().department())
            );
        }
        return stores;
    }

}
