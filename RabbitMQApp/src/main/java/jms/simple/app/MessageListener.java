package jms.simple.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

    private static final String QUEUE_NAME_1 = "FIRST.Q";
    private static final String QUEUE_NAME_2 = "SECOND.Q";
    private static final String QUEUE_NAME_3 = "THIRD.Q";

    @RabbitListener(queues = QUEUE_NAME_1)
    public void onMessageFromFirstQueue(UserMessage userMessage) {
        logger.info("New message from FIRST.Q {}", userMessage);
    }

    @RabbitListener(queues = QUEUE_NAME_2)
    public void onMessageFromSecondQueue(UserMessage userMessage) {
        logger.info("New message from SECOND.Q {}", userMessage);
    }

    @RabbitListener(queues = QUEUE_NAME_3)
    public void onMessageFromThirdQueue(UserMessage userMessage) {
        logger.info("New message from THIRD.Q {}", userMessage);
    }
}
