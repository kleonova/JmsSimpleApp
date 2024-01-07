package jms.simple.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private final String QUEUE_NAME = "TEST.QUEUE";
    private final String TOPIC_NAME = "TEST.TOPIC";

    @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsQueueListenerContainerFactory")
    public void onMessageFromQueue(UserMessage userMessage) {
        logger.info("New message received from queue {}", userMessage);
    }

    @JmsListener(destination = TOPIC_NAME, containerFactory = "jmsTopicListenerContainerFactory")
    public void onMessageFromTopic(UserMessage userMessage) {
        logger.info("New message received from topic {}", userMessage);
    }
}
