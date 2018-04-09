package hello;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		System.out.println("File reading started");
		// String encryptData1 = new
		// Application().getFileWithUtil("/gs-spring-boot-complete/src/main/java/hello/Encrypt.json");
		String encryptData1 = "{\"name\" : \"nikhil\",\"org\" : \"neu\" ,\"roles\" : [\"GET\"]}";
		System.out.println("File reading done");
		String s = null;
		try {
			s = GenerateToken.encrypt(encryptData1);
			GenerateToken.decrypt(s);
		} catch (Exception e) {
			System.out.println("Encryption error " + e.getMessage());
		}

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

	private String getFileWithUtil(String fileName) {

		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	// @Bean
	// JedisConnectionFactory jedisConnectionFactory() {
	// return new JedisConnectionFactory();
	// }
	//
	// @Bean
	// public RedisTemplate<String, Object> redisTemplate() {
	// final RedisTemplate<String, Object> template = new RedisTemplate<String,
	// Object>();
	// template.setConnectionFactory(jedisConnectionFactory());
	// template.setValueSerializer(new
	// GenericToStringSerializer<Object>(Object.class));
	// return template;
	// }

	@Bean
	Queue queue() {
		return new Queue("planQueue");
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("planExchange");
	}

	@Bean
	AmqpTemplate create(ConnectionFactory connectionFactory) {
		AmqpTemplate template = new RabbitTemplate(connectionFactory);
		AmqpAdmin admin = new RabbitAdmin(connectionFactory);
		admin.declareQueue(queue());
		admin.declareExchange(exchange());
		admin.declareBinding(new Binding(queue().getName(), DestinationType.QUEUE, exchange().getName(), "planKey",
				new HashMap<>()));
		return template;
	}
	
	@Bean
	MessagePublisher rabbitPub(ConnectionFactory connectionFactory){
		return new RabbitMessagePublisher(create(connectionFactory));
	}

	// @Bean
	// ChannelTopic topic() {
	// return new ChannelTopic("messageQueue");
	// }
	//
	// @Bean
	// MessagePublisher redisPublisher() {
	// return new RedisMessagePublisher(redisTemplate(), topic());
	// }
}
