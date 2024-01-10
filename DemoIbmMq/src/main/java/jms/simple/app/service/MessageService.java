package jms.simple.app.service;

import com.ibm.mq.jakarta.jms.MQQueue;
import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import jms.simple.app.model.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class MessageService {
    @Value("${ibm.mq.sendingQueueName:DEV.QUEUE.1}")
    String SEND_QUEUE_NAME;

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage(MessageModel message) {
        try {
            MQQueue queue = new MQQueue(SEND_QUEUE_NAME);
            jmsTemplate.convertAndSend(queue, message.getMessage(), textMessage -> {
                textMessage.setJMSCorrelationID(message.getIdentifier());
                return textMessage;
            });
            log.info("Send message {} to queue {}", message, SEND_QUEUE_NAME);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Deprecated // this was just to show how to find a message by correlation Id
    public String findMessage(String correlationId) {
        String message = null;

        String convertedId = bytesToHex(correlationId.getBytes());
        final String selectorExpression = String.format("JMSCorrelationID='ID:%s'", convertedId);

        try {
            final TextMessage responseMessage = (TextMessage) jmsTemplate.receiveSelected(SEND_QUEUE_NAME, selectorExpression);
            message = responseMessage.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }

        log.info("Found message {} with correlationId {}", message, correlationId);

        return message;
    }


    // You could use Apache Commons Codec library instead
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes();
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }
}
