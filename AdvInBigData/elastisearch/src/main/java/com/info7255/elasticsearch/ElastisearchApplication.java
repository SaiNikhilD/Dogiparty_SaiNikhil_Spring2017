package com.info7255.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElastisearchApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElastisearchApplication.class);

	// @Bean
	// MessageListenerAdapter messageListener() {
	// return new MessageListenerAdapter(new RedisListner());
	// }
	//
	// @Bean
	// JedisConnectionFactory jedisConnectionFactory() {
	// return new JedisConnectionFactory();
	// }
	//
	// @Bean
	// RedisMessageListenerContainer redisContainer() {
	// RedisMessageListenerContainer container = new
	// RedisMessageListenerContainer();
	// container.setConnectionFactory(jedisConnectionFactory());
	// container.addMessageListener(messageListener(), topic());
	// return container;
	// }

	// @Bean
	// ChannelTopic topic() {
	// return new ChannelTopic("messageQueue");
	// }

//	@Bean
//	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//			MessageListenerAdapter listenerAdapter) {
//		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		container.setQueueNames("planQueue");
//		container.setMessageListener(listenerAdapter);
//		return container;
//	}
//
//	MessageListenerAdapter adapter() {
//		Object listener = new Object() {
//			public void handleMessage(String foo) {
//				System.out.println(foo);
//			}
//		};
//		MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
//		return adapter;
//	}
	
	public static void main(String[] args) throws InterruptedException {
		
		SpringApplication.run(ElastisearchApplication.class, args);

	}

}
