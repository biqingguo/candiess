package com.itranswarp.crypto.candiess.web.proxy;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.itranswarp.crypto.candiess.mq.annotation.RocketListener;
import com.itranswarp.crypto.candiess.mq.annotation.RocketOnMessage;
import com.itranswarp.crypto.candiess.mq.biz.SendMqmsgListener;
import com.itranswarp.crypto.candiess.mq.consumer.MetaQConsumerClient;
import com.itranswarp.crypto.candiess.mq.consumer.MetaQEventClient;

@Component
public class RocketMqProxy {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${crypto.manage.mq.name-srv}")
	private String nameSrv;
	@Value("${crypto.manage.mq.consumer-group}")
	private String consumerGroup;
	@Value("${crypto.manage.mq.consume-thread-max}")
	private String consumeThreadMax;
	@Value("${crypto.manage.mq.consume-thread-min}")
	private String consumeThreadMin;
	@Autowired
	private ApplicationContext applicationContext;
	/** 消费的mq client */
	private Map<String/** topicname */
	, MetaQConsumerClient> consumerMap = new ConcurrentHashMap<String, MetaQConsumerClient>();

	/** 消费的mq linster */
	private Map<String/** topic */
	, SendMqmsgListener> consumerLinsterMap = new ConcurrentHashMap<String, SendMqmsgListener>();
	/**
	 * bean map 保存需要调用的bean集合
	 */
	Map<String/* topic */, Map<String/* tag */, RocketOnMessage>> rocketOnMessage;

	@PostConstruct
	public void init() {
		logger.info("rocketMq开始初始化监听,nameser:{}", nameSrv);
		MetaQEventClient metaQEventClient = new MetaQEventClient();
		metaQEventClient.setConsumerGroup(consumerGroup);
		metaQEventClient.setConsumeThreadMax(consumeThreadMax);
		metaQEventClient.setConsumeThreadMin(consumeThreadMin);
		metaQEventClient.setNamesrvAddr(nameSrv);
		SendMqmsgListener listerner = new SendMqmsgListener();
		rocketOnMessage = new ConcurrentHashMap<>();
		listerner.setRocketOnMessageMap(rocketOnMessage);
		metaQEventClient.setMessageListener(listerner);
		Map<String, String> map = new ConcurrentHashMap<>();
		metaQEventClient.setSubscribeMap(map);
		metaQEventClient.setInstanceName("manage");
		Map<String, RocketOnMessage> beanMap = applicationContext.getBeansOfType(RocketOnMessage.class);
		for (Entry<String, RocketOnMessage> entry : beanMap.entrySet()) {
			RocketOnMessage value = entry.getValue();
			RocketListener annotation = value.getClass().getAnnotation(RocketListener.class);
			String tag = annotation.tag();
			String topic = annotation.topic();
			map.put(topic, tag);
			if (!rocketOnMessage.containsKey(topic)) {
				rocketOnMessage.put(topic, new ConcurrentHashMap<>());
			}
			Map<String, RocketOnMessage> tagMap = rocketOnMessage.get(topic);
			tagMap.put(tag, value);
		}
		try {
			metaQEventClient.init();
		} catch (MQClientException e) {
			logger.error("rocketMq,初始化监听异常:{}", ExceptionUtils.getStackTrace(e));
		}
	}
}
