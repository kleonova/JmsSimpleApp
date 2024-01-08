package jms.simple.app;

import jakarta.jms.JMSException;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MQCommunication {

    @Value("${ibm.mq.sendingQueueName:DEV.QUEUE.1}")
    String nameOfSendingQueue;

    @Value("${ibm.mq.receivingQueueName:DEV.QUEUE.2}")
    String nameOfReceivingQueue;


    private final ApplicationContext applicationContext;

    private ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * This function take over the message as string and send it to define MQ Server (via properties)
     * and define queue
     *
     * @param mqString message which has to be send
     * @throws JmsException in the case if there is no connection or parameter don't exist
     */
    public void sendMessageToMQ(String mqString) throws JmsException {
        JmsTemplate jmsTemplate = getApplicationContext().getBean(JmsTemplate.class);
        try {
            jmsTemplate.convertAndSend(nameOfSendingQueue, mqString);
        } catch (JmsException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public String readMessageFromMq() throws JMSException {
        JmsTemplate jmsTemplate = getApplicationContext().getBean(JmsTemplate.class);
        TextMessage textMessage = (TextMessage) jmsTemplate.receive(nameOfReceivingQueue);
        assert textMessage != null;
        String stringFromQueue;
        try {
            stringFromQueue = textMessage.getText();
        } catch (JMSException e) {
            log.error(e.getMessage());
            throw e;
        }
        return stringFromQueue;
    }
}
