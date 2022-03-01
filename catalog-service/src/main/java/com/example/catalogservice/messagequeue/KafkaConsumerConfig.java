package com.example.catalogservice.messagequeue;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> properties = new HashMap<>();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		// 토픽에 쌓여있는 메시지를 가져가는 Consumer 들을 Grouping 할 때 사용 (현재는 1개라 의미 X)
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(properties);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory
			= new ConcurrentKafkaListenerContainerFactory<>();
		kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

		return kafkaListenerContainerFactory;
	}

}