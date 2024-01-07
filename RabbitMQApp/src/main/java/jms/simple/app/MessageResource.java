package jms.simple.app;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class MessageResource {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/send/{exchange}/{key}")
    public String sendMessage(@PathVariable String exchange,
                              @PathVariable String key,
                              @RequestBody UserMessage userMessage) {

        userMessage.setTimestamp(LocalDateTime.now());
        amqpTemplate.convertAndSend(exchange, key, userMessage);

        return "Message sent to " + exchange + " with routing key " + key;
    }
}
