import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Order;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.dataGen.BaseKafkaProducer;
import util.dataGen.OrdersProducer;

import java.util.ArrayList;
import java.util.List;

public class BranchedStreams {
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private static Logger logger = LoggerFactory.getLogger(BranchedStreams.class);
    private static Producer<String, String> producer;
    private static Callback callback;
    private static final String ORDERS_TOPIC = "orders-topic";

    private static void init() {
        if (producer == null) {
            producer = new BaseKafkaProducer().getProducer();
            callback = (metadata, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                }
            };
        }
    }

    public static void main(String args[]){
        init();
        List<Order> orders =  OrdersProducer.generateOrders(10);
        List<String> orderJson = convertToJson(orders);

        for(String order: orderJson){
            ProducerRecord<String, String> record = new ProducerRecord<>(ORDERS_TOPIC,  null, order);
            producer.send(record, callback);
            logger.info(order);
        }
        producer.close();
    }


    private static <T> List<String> convertToJson(List<T> items){
        List<String> jsonList = new ArrayList<>();
        for(T item: items){
            jsonList.add(convertToJson(item));
        }
        return jsonList;
    }

    private static <T> String convertToJson(T item){ return gson.toJson(item); }
}
