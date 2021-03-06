package mico.wiretap;

import java.util.HashMap;
import java.util.Map;

import io.cloudevents.CloudEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        //props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return props;
    }

/*    @Bean
    public ProducerFactory<String, CloudEvent> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }*/

    @Bean
    public ProducerFactory<String, String> producerFactoryString() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

/*
    @Bean
    public KafkaTemplate<String, CloudEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
*/

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactoryString());
    }

    @Bean
    public KafkaProducer sender() {
        return new KafkaProducer();
    }
}
