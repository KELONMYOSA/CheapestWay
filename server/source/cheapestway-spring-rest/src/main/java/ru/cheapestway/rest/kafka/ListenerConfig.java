package ru.cheapestway.rest.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.cheapestway.rest.models.MessageWS;

import java.util.HashMap;
import java.util.Map;

import static ru.cheapestway.rest.Main.KAFKA_SERVER;

@EnableKafka
@Configuration
public class ListenerConfig {
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, MessageWS> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageWS> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, MessageWS> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer(MessageWS.class));
    }

    @Bean
    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, "to-from-rest");
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configurations.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 10485760);
        return configurations;
    }
}
