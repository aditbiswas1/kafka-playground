package util.dataGen;

import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NameProducer {
    private static Producer<String, String> producer;
    private static Callback callback;
    private static Logger logger = LoggerFactory.getLogger(NameProducer.class);
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static final String NAME_EXAMPLE_TOPIC = "names-topic";

    private static void init() {
        if (producer == null) {
            producer = new BaseKafkaProducer().getProducer();
            callback = (metadata, exception) -> {
                if (exception != null) {
                    exception.printStackTrace();
                }
            };
            logger.info("Producer initialized");
        }
    }

    public static void generateNames() {
        Runnable generateName = () -> {
            init();
            int counter=0;
            while(counter++ < 15){
                Faker faker = new Faker();
                String value = faker.name().fullName();
                ProducerRecord<String, String> record = new ProducerRecord<>(NAME_EXAMPLE_TOPIC,  null, value);
                producer.send(record, callback);
                logger.info("sent batch");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    Thread.interrupted();
                }
            }

        };
        executorService.submit(generateName);
    }

    public static void shutdown(){
        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;
        }
        if (producer != null) {
            producer.close();
            producer = null;
        }
    }
}
