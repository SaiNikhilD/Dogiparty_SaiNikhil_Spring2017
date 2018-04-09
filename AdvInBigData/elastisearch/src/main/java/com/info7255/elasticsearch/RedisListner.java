package com.info7255.elasticsearch;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;

public class RedisListner{ 
//implements MessageListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisListner.class);

//	@Override
//	public void onMessage(Message message, byte[] pattern) {
//		LOGGER.info("Received <" + message.toString() + ">");
//
//		String[] data = message.toString().split("@ID");
//
//		JestClientFactory factory = new JestClientFactory();
//		factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200").build());
//
//		if (factory == null) {
//			LOGGER.info("Factory is null");
//		}
//
//		JestClient client = factory.getObject();
//
//		Index index = new Index.Builder(data[1]).index("plan").type("type").id(data[0]).build();
//
//		try {
//			client.execute(index);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
