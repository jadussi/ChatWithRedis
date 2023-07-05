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
	
	// 톡 관련 전역변수 선언 (개인톡, 단톡 구분 필요 X)
	private String CHAT_QUEUE = "chat.queue";
	private String CHAT_EXCHANGE = "chat.exchange";
	private String CHAT_KEY = "chat.key.*";
	
	@Bean	// 채팅방 큐 생성
	public Queue chatQueue() {
		return new Queue(CHAT_QUEUE);
	}
	
	@Bean	// 채팅방 Topic 방식 Exchange 생성
	public TopicExchange chatExchange() {
		return new TopicExchange(CHAT_EXCHANGE);
	}
	
	@Bean	// Queue, exchange 바인딩
	public Binding chatBinding(@Qualifier("chatQueue") Queue queue, @Qualifier("chatExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(CHAT_KEY);
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
