package com.info7255.elasticsearch;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;

@Component
public class RabbitListner {

	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitListener.class);
	
	@RabbitListener(queues="planQueue")
	public void process(String message){
		
		LOGGER.info("Reeived: "+ message);
		
		String[] data = message.toString().split("@ID");

		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200").build());

		JestClient client = factory.getObject();

		Index index = new Index.Builder(data[1]).index("plan").type("type").id(data[0]).build();

		try {
			client.execute(index);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
