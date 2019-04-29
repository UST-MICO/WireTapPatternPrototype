package mico.wiretap;

import io.cloudevents.CloudEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

public class KafkaConsumer {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(KafkaConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "consumer")
    public void receive(String payload) {
        LOGGER.info("received payload='{}'", payload);
        LOGGER.info("consumer");
        latch.countDown();
    }


/*    @KafkaListener(topics = "consumer1")
    public void receive(CloudEvent payload) {
        LOGGER.info("received payload='{}'", payload);
        LOGGER.info(payload.getId());
        latch.countDown();
    }*/

}
