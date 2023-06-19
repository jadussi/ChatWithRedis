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

@EnableRabbit
@Configuration
public class RabbitConfig {
	
	// ������ ���� �������� ����
	private static final String PRIVATE_CHAT_QUEUE = "privateChat.queue";
	private static final String PRIVATE_CHAT_EXCHAGE = "privateChat.exchage";
	private static final String PRIVATE_CHAT_KEY = "privateChat.key.*";	// * �ϳ��� �ܾ ��ü�ϴ� ���ϵ�ī��, # �������� �ܾ ��ü�ϴ� ���ϵ�ī��
	
	@Bean	// ������ ť ����
	Queue privateChatQueue() {
		return new Queue(PRIVATE_CHAT_QUEUE);
	}
	
	@Bean	// ������ Direct ��� Exchange ���� 
	DirectExchange privateChatExchange () {
		return new DirectExchange(PRIVATE_CHAT_EXCHAGE);
	}
	
	@Bean	// ������ privateChat.key.* ����� Ű�� ����Ͽ� ť, exchange ���ε� ����
	Binding privateChatBinding(@Qualifier("privateChatQueue") Queue queue, @Qualifier("privateChatExchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(PRIVATE_CHAT_KEY);
	}
	
	// ��ü�� ���� �������� ����
	private static final String PUBLIC_CHAT_QUEUE = "publicChat.queue";
	private static final String PUBLIC_CHAT_EXCHAGE = "publicChat.exchage";
	private static final String PUBLIC_CHAT_KEY = "puvlicChat.key.*";
	
	@Bean	// ��ü�� ť ����
	Queue publicChatQueue() {
		return new Queue(PUBLIC_CHAT_QUEUE);
	}
	
	@Bean	// ��ü�� topic ��� exchange ����
	TopicExchange publicChatExchange() {
		return new TopicExchange(PUBLIC_CHAT_EXCHAGE);
	}
	
	@Bean	// ��ü�� publicChat.key.* ����� Ű�� ����Ͽ� ť, exchange ���ε� ����
	Binding publicChatBinding(@Qualifier("publicChatQueue") Queue queue, @Qualifier("publicChatExchange") TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(PUBLIC_CHAT_KEY);
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
