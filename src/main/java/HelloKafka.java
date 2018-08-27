import util.dataGen.NameProducer;

import java.util.concurrent.ExecutionException;

public class HelloKafka {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NameProducer.generateNames();
    }
}
