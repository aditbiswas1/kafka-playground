import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.dataGen.NameProducer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class HelloKafka {

    private static Logger logger = LoggerFactory.getLogger(HelloKafka.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // generate dummy data
        NameProducer.generateNames();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "name_upper_app_id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");


        Serde<String> stringSerde = Serdes.String();
        StreamsBuilder builder = new StreamsBuilder();

        builder.stream("names-topic", Consumed.with(stringSerde, stringSerde))
                .mapValues((ValueMapper<String, String>) String::toUpperCase)
                .through("upper-names-topic", Produced.with(stringSerde, stringSerde))
                .print(Printed.<String, String>toSysOut().withLabel("Upper case names App"));

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), props);

        kafkaStreams.start();
        Thread.sleep(35000);
        logger.info("Shutting down the Upper case names app now");
        kafkaStreams.close();
        NameProducer.shutdown();
    }
}
