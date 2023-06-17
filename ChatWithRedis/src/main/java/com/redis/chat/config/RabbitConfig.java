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
	
	// 개인톡 관련 전역변수 선언
	private static final String PRIVATE_CHAT_QUEUE = "privateChat.queue";
	private static final String PRIVATE_CHAT_EXCHAGE = "privateChat.exchage";
	private static final String PRIVATE_CHAT_KEY = "privateChat.key.*";	// * 하나의 단어만 대체하는 와일드카드, # 여러개의 단어를 대체하는 와일드카드
	
	@Bean	// 개인톡 큐 생성
	Queue privateChatQueue() {
		return new Queue(PRIVATE_CHAT_QUEUE);
	}
	
	@Bean	// 개인톡 Direct 방식 Exchange 생성 
	DirectExchange privateChatExchange () {
		return new DirectExchange(PRIVATE_CHAT_EXCHAGE);
	}
	
	@Bean	// 개인톡 privateChat.key.* 라우팅 키를 사용하여 큐, exchange 바인딩 설정
	Binding privateChatBinding(@Qualifier("privateChatQueue") Queue queue, @Qualifier("privateChatExchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(PRIVATE_CHAT_KEY);
	}
	
	// 단체톡 관련 전역번수 선언
	private static final String PUBLIC_CHAT_QUEUE = "publicChat.queue";
	private static final String PUBLIC_CHAT_EXCHAGE = "publicChat.exchage";
	private static final String PUBLIC_CHAT_KEY = "puvlicChat.key.*";
	
	@Bean	// 단체톡 큐 생성
	Queue publicChatQueue() {
		return new Queue(PUBLIC_CHAT_QUEUE);
	}
	
	@Bean	// 단체톡 topic 방식 exchange 생성
	TopicExchange publicChatExchange() {
		return new TopicExchange(PUBLIC_CHAT_EXCHAGE);
	}
	
	@Bean	// 단체톡 publicChat.key.* 라우팅 키를 사용하여 큐, exchange 바인딩 설정
	Binding publicChatBinding(@Qualifier("publicChatQueue") Queue queue, @Qualifier("publicChatExchange") TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(PUBLIC_CHAT_KEY);
	}
	
	@Bean
    public MessageConverter messageConverter() {	
        return new Jackson2JsonMessageConverter();	// json형식의 messageConverter 설정
    }
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());	//object에 담을 message rabbitmq의 메세지 형식으로 변환
		return rabbitTemplate;
	}
}
