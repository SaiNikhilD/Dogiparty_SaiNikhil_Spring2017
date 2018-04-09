package hello;

import org.springframework.amqp.core.AmqpTemplate;

public class RabbitMessagePublisher implements MessagePublisher {
	
	AmqpTemplate template;
	
	RabbitMessagePublisher(AmqpTemplate template){
		this.template = template;
	}

	@Override
	public void publish(String message) {
		template.convertAndSend("planExchange", "planKey", message);
		
	}

}
