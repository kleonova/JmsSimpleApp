package jms.simple.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.jms.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
public class SenderApplication {

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setObjectMapper(objectMapper);
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");

		return converter;
	}

	@Bean
	public JmsTemplate jmsQueueTemplate(ConnectionFactory connectionFactory,
										MessageConverter jacksonJmsMessageConverter) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);

		return jmsTemplate;
	}

	@Bean
	public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory,
										MessageConverter jacksonJmsMessageConverter) {
		JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
		jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);
		jmsTemplate.setPubSubDomain(true);

		return jmsTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

}
