package jms.simple.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class MessageResource {
    private static final Logger logger = LoggerFactory.getLogger(MessageResource.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/send/{exchange}/{key}")
    public String sendMessage(@PathVariable String exchange,
                              @PathVariable String key,
                              @RequestBody UserMessage userMessage) {

        userMessage.setTimestamp(LocalDateTime.now());
        amqpTemplate.convertAndSend(exchange, key, userMessage);
        logger.info("Message sent to {} with key {}, message - {}", exchange, key, userMessage);

        return "Message sent to " + exchange + " with routing key " + key;
    }
}
