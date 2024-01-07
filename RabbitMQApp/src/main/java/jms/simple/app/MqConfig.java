package jms.simple.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    private final String QUEUE_NAME_1 = "FIRST.Q";
    private final String QUEUE_NAME_2 = "SECOND.Q";
    private final String QUEUE_NAME_3 = "THIRD.Q";

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    Queue firstQueue() {
        return new Queue(QUEUE_NAME_1, false); //true - persistent
    }

    @Bean
    Queue secondQueue() {
        return new Queue(QUEUE_NAME_2, false);
    }

    @Bean
    Queue thirdQueue() {
        return new Queue(QUEUE_NAME_3, false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("our.direct.exchange");
    }

    @Bean
    Binding firstQueueToDirectExchangeBinding() {
        return BindingBuilder
                .bind(firstQueue())
                .to(directExchange())
                .with("first.queue.key");
    }

    @Bean
    Binding secondQueueToDirectExchangeBinding() {
        return BindingBuilder
                .bind(secondQueue())
                .to(directExchange())
                .with("second.queue.key");
    }
}
