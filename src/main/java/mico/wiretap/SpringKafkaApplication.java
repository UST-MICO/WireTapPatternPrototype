package mico.wiretap;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringKafkaApplication implements CommandLineRunner {
    public static Logger logger = LoggerFactory.getLogger(SpringKafkaApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaApplication.class, args);
    }


    @Autowired
    private KafkaTemplate<String, String> template;
    @Autowired
    private KafkaConsumer consumer;


    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(String... args) throws Exception {
        this.template.send("wiretap", "foo1");
        this.template.send("wiretap", "foo2");
        this.template.send("wiretap", "foo3");
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
        consumer.getLatch().await(100000, TimeUnit.MILLISECONDS);

    }

    @KafkaListener(topics = "wiretap")
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        this.template.send("conumer1", cr.toString());
        this.template.send("conumer2", cr.toString());
    }


}
