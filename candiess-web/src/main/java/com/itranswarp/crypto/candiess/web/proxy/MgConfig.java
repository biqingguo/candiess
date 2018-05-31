package com.itranswarp.crypto.candiess.web.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itranswarp.crypto.candiess.mq.producer.MetaqMessagePublisher;
import com.itranswarp.crypto.candiess.mq.producer.MetaqMessagePublisherImpl;

@Configuration
public class MgConfig {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${crypto.manage.mq.name-srv}")
	private String nameSrv;
	@Value("${crypto.manage.mq.consumer-group}")
	private String consumerGroup;

	@Bean
	public MetaqMessagePublisher createMetaq() {
		logger.info("rocketMq生产者nameService地址:{}", nameSrv);
		logger.info("rocketMq生产者consumerGroup消费组:{}", consumerGroup);
		MetaqMessagePublisherImpl entity = new MetaqMessagePublisherImpl();
		entity.setGroupId(consumerGroup);
		entity.setNamesrvaddr(nameSrv);
		entity.init();
		logger.info("rocketMq生产者初始化完成");
		return entity;
	}

}
