package mico.wiretap;

import io.cloudevents.CloudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(KafkaProducer.class);

/*
    @Autowired
    private KafkaTemplate<String, CloudEvent> kafkaTemplate;
*/

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateString;
/*
    public void send(CloudEvent payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplate.send("wiretap", payload);
    }*/

    // String version
    public void send(String payload) {
        LOGGER.info("sending payload='{}'", payload);
        kafkaTemplateString.send("wiretap", payload);
    }
}
