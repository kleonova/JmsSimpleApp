package jms.simple.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class MessageResource {
    private static final Logger logger = LoggerFactory.getLogger(MessageResource.class);

    private final String QUEUE_NAME = "TEST.QUEUE";
    private final String TOPIC_NAME = "TEST.TOPIC";

    @Qualifier("jmsQueueTemplate")
    @Autowired
    private JmsTemplate jmsQueueTemplate;

    @Qualifier("jmsTopicTemplate")
    @Autowired
    private JmsTemplate jmsTopicTemplate;

    @PostMapping("/send/queue")
    public String sendToQueue(@RequestBody UserMessage userMessage) {
        userMessage.setTimestamp(LocalDateTime.now());
        jmsQueueTemplate.convertAndSend(QUEUE_NAME, userMessage);
        logger.info("Message sent to queue {}", userMessage);

        return "Message sent to queue";
    }

    @PostMapping("/send/topic")
    public String sendToTopic(@RequestBody UserMessage userMessage) {
        userMessage.setTimestamp(LocalDateTime.now());
        jmsTopicTemplate.convertAndSend(TOPIC_NAME, userMessage);
        logger.info("Message sent to topic {}", userMessage);

        return "Message sent to topic";
    }
}
