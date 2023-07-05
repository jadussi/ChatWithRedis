package com.redis.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.AMQP.Exchange;

@EnableRabbit
@Configuration
public class RabbitConfig {
	
	// �� ���� �������� ���� (������, ���� ���� �ʿ� X)
	private String CHAT_QUEUE = "chat.queue";
	private String CHAT_EXCHANGE = "chat.exchange";
	private String CHAT_KEY = "chat.key.*";
	
	@Bean	// ä�ù� ť ����
	public Queue chatQueue() {
		return new Queue(CHAT_QUEUE);
	}
	
	@Bean	// ä�ù� Topic ��� Exchange ����
	public TopicExchange chatExchange() {
		return new TopicExchange(CHAT_EXCHANGE);
	}
	
	@Bean	// Queue, exchange ���ε�
	public Binding chatBinding(@Qualifier("chatQueue") Queue queue, @Qualifier("chatExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CHAT_KEY);
	}
	
	@Bean
    public MessageConverter messageConverter() {	
        return new Jackson2JsonMessageConverter();	// json������ messageConverter ����
    }
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());	//object�� ���� message rabbitmq�� �޼��� �������� ��ȯ
		return rabbitTemplate;
	}
}
