package util.dataGen;

import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class BaseKafkaProducer {
    private static Logger logger = LoggerFactory.getLogger(BaseKafkaProducer.class);
    private static Producer<String, String> producer;

    public BaseKafkaProducer() {
        if (producer == null) {
            logger.info("Initializing the producer");
            Properties properties = new Properties();
            properties.put("bootstrap.servers", "localhost:9092");
            properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            properties.put("acks", "1");
            properties.put("retries", "3");

            producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
            logger.info("Producer initialized");
        }
    }
    public Producer getProducer(){
        return producer;
    }

    public void close(){
        producer.close();
    }
}
