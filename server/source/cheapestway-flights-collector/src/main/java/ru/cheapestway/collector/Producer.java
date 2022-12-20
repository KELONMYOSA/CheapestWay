package ru.cheapestway.collector;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {
    public static KafkaProducer openProducer(String server) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "1");

        KafkaProducer producer = new KafkaProducer(props);

        return producer;
    }

    public static void sendMessage(KafkaProducer producer, String topic, String key, String message) {
        producer.send(new ProducerRecord(topic, key, message), (recordMetadata, exception) -> {
            if (exception == null) {
                System.out.println("Offset: " +
                        recordMetadata.offset() + ", Timestamp: " +
                        recordMetadata.timestamp() + ", Partition: " +
                        recordMetadata.partition());
            } else {
                System.err.println("An error occurred");
                exception.printStackTrace(System.err);
            }
        });
    }

    public static void closeProducer(KafkaProducer producer) {
        producer.flush();
        producer.close();
    }
}
