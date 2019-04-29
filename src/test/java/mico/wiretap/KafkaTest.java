package mico.wiretap;


import io.cloudevents.CloudEvent;
import io.cloudevents.CloudEventBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1,
        topics = {KafkaTest.TOPIC})
public class KafkaTest {

    static final String TOPIC = "consumer1";

    /*@Autowired
    private KafkaWireTap wiretap;*/
    @Autowired
    private KafkaConsumer consumer1;
    @Autowired
    private KafkaConsumer consumer2;

    @Autowired
    private KafkaProducer producer;

    final String eventId = UUID.randomUUID().toString();
    final URI src = URI.create("/trigger");
    final String eventType = "String";
    final String payload = "payload";

    // passing in the given attributes
    final CloudEvent cloudEvent = new CloudEventBuilder()
            .type(eventType)
            .id(eventId)
            .source(src)
            .data(payload)
            .contentType("String")
            .build();

    @Test
    public void testReceive() throws Exception {

        producer.send("cloudEvent");

        /*wiretap.getLatch().await(100000, TimeUnit.MILLISECONDS);
        assertThat(wiretap.getLatch().getCount()).isEqualTo(0);*/

        consumer1.getLatch().await(100000, TimeUnit.MILLISECONDS);
        assertThat(consumer1.getLatch().getCount()).isEqualTo(0);

/*        consumer2.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(consumer2.getLatch().getCount()).isEqualTo(0);*/

    }
}
