package jms.simple.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    private final String QUEUE_NAME_1 = "FIRST.Q";
    private final String QUEUE_NAME_2 = "SECOND.Q";
    private final String QUEUE_NAME_3 = "THIRD.Q";
    private final String DIRECT_EXCHANGE_NAME = "our.direct.exchange";
    private final String TOPIC_EXCHANGE_NAME = "our.topic.exchange";
    private final String FANOUT_EXCHANGE_NAME = "our.fanout.exchange";
    private final String ROUTING_KEY_NAME_1 = "first.queue.key";
    private final String ROUTING_KEY_NAME_2 = "second.queue.key";
    private final String ROUTING_PATTERN = "#.key"; // # - любое число слов, * - одно слово

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
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    Binding firstQueueToDirectExchangeBinding() {
        return BindingBuilder
                .bind(firstQueue())
                .to(directExchange())
                .with(ROUTING_KEY_NAME_1);
    }

    @Bean
    Binding secondQueueToDirectExchangeBinding() {
        return BindingBuilder
                .bind(secondQueue())
                .to(directExchange())
                .with(ROUTING_KEY_NAME_2);
    }

    @Bean
    Binding secondQueueToTopicExchangeBinding() {
        return BindingBuilder
                .bind(secondQueue())
                .to(topicExchange())
                .with(ROUTING_PATTERN);
    }

    @Bean
    Binding thirdQueueToTopicExchangeBinding() {
        return BindingBuilder
                .bind(thirdQueue())
                .to(topicExchange())
                .with(ROUTING_PATTERN);
    }

    @Bean
    Binding firstQueueToFanoutExchangeBinding() {
        return BindingBuilder
                .bind(firstQueue())
                .to(fanoutExchange());
    }

    @Bean
    Binding thirdQueueToFanoutExchangeBinding() {
        return BindingBuilder
                .bind(thirdQueue())
                .to(fanoutExchange());
    }
}
