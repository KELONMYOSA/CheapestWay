package ru.cheapestway.rest.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.cheapestway.rest.models.MessageWS;

@Component
public class MessageListener {
    @Autowired
    SimpMessagingTemplate template;

    @KafkaListener(
            topics = "to-from-rest",
            id = "to-from-rest"
    )
    public void listen(MessageWS message) {
        template.convertAndSend("/user/" + message.getUserID() + "/queue/messages", message);
    }
}
