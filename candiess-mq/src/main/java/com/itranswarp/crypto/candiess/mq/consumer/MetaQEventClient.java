/**
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.itranswarp.crypto.candiess.mq.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itranswarp.crypto.candiess.mq.biz.SendMqmsgListener;

/**
 * MetaQ数据接收客户端，主要功能： -- 订阅metaQ数据
 * 
 * @author jin.qian
 * @version $Id: MetaQEventClient.java, v 0.1 2015年10月23日 下午8:46:58 jin.qian Exp
 *          $
 */
public class MetaQEventClient implements MetaQConsumerClient {

	private static final Logger logger = LoggerFactory.getLogger(MetaQEventClient.class);

	private String instanceName;
	private String consumerGroup;
	private String namesrvAddr;
	private String consumeThreadMax;
	private String consumeThreadMin;

	/** metaQ 数据消费者 */
	protected DefaultMQPushConsumer consumer;

	/** MetaQ 相关参数配置 */
	private Map<String/** topic */
	, String/** tags */
	> subscribeMap = new HashMap<String, String>();

	/** metaq 并发线数 */
	protected int consumerCorePoolSize = 1;

	/** metaq 消费挂起 */
	protected boolean consumerSuspended = false;

	/** 处理进程 */
	private MessageListenerConcurrently messageListener;

	/**
	 * metaq consumer内部具有并发能力，并发数可配置，建议应用这边只起一个consumer，利于并发性能调整。
	 * 
	 * @throws InterruptedException
	 * @throws MQClientException
	 */
	public void init() throws MQClientException {
		consumer = new DefaultMQPushConsumer("manage");
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setInstanceName(instanceName);
		consumer.setConsumerGroup(consumerGroup);
		//consumer.setInstanceName("consumer");
		consumer.setConsumeThreadMin(Integer.parseInt(consumeThreadMin));
		consumer.setConsumeThreadMax(Integer.parseInt(consumeThreadMax));
		// 重上次消费进度消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// consumer.setConsumeTimeout(1000);
		// 添加metaQ订阅关系
		for (String topic : subscribeMap.keySet()) {
			consumer.subscribe(topic, subscribeMap.get(topic));
		}
		if (consumerSuspended) {
			consumer.suspend();
		} else {
			consumer.resume();
		}

		// 挂载数据消费实例
		consumer.registerMessageListener(messageListener);

		consumer.start();
	}

	public void shown() {
		consumer.shutdown();
	}

	/**
	 * @see rocketmq.util.consumer.alipay.databus.input.metaq.MetaQConsumerClient#suspendConsumer()
	 */
	public void suspendConsumer() {
		consumer.suspend();
	}

	/**
	 * @see rocketmq.util.consumer.alipay.databus.input.metaq.MetaQConsumerClient#resumeConsumer()
	 */
	public void resumeConsumer() {
		consumer.resume();
	}

	/**
	 * @see rocketmq.util.consumer.alipay.databus.input.metaq.MetaQConsumerClient#updateConsumerCorePoolSize()
	 */
	public void updateConsumerCorePoolSize(int corePoolSize) {
		consumer.updateCorePoolSize(corePoolSize);
	}

	/**
	 * @see rocketmq.util.consumer.alipay.databus.input.metaq.MetaQConsumerClient#viewMessage(String)
	 */
	public MessageExt viewMessage(String msgId) throws Exception {
		if (StringUtils.isEmpty(msgId)) {
			return null;
		}
		return consumer.viewMessage(msgId);
	}

	/**
	 * setConsumerCorePoolSize
	 * 
	 * @painaram consumerCorePoolSize
	 */
	public void setConsumerCorePoolSize(int consumerCorePoolSize) {
		this.consumerCorePoolSize = consumerCorePoolSize;
	}

	/**
	 * setConsumerSuspended
	 * 
	 * @param consumerSuspended
	 */
	public void setConsumerSuspended(boolean consumerSuspended) {
		this.consumerSuspended = consumerSuspended;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getConsumeThreadMax() {
		return consumeThreadMax;
	}

	public void setConsumeThreadMax(String consumeThreadMax) {
		this.consumeThreadMax = consumeThreadMax;
	}

	public String getConsumeThreadMin() {
		return consumeThreadMin;
	}

	public void setConsumeThreadMin(String consumeThreadMin) {
		this.consumeThreadMin = consumeThreadMin;
	}

	public boolean isConsumerSuspended() {
		return consumerSuspended;
	}

	public void suspend() {
		if (!consumerSuspended) {
			consumer.suspend();
			consumerSuspended = true;
		}
	}

	public void resume() {
		if (consumerSuspended) {
			consumer.resume();
			consumerSuspended = false;
		}
	}

	public MessageListenerConcurrently getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListenerConcurrently messageListener) {
		this.messageListener = messageListener;
	}

	public void setSubscribeMap(Map<String, String> subscribeMap) {
		this.subscribeMap = subscribeMap;
	}

	public static void main(String[] args) {
		MetaQEventClient metaQEventClient = new MetaQEventClient();
		metaQEventClient.setConsumerGroup("manage");
		metaQEventClient.setConsumeThreadMax(1 + "");
		metaQEventClient.setConsumeThreadMin(1 + "");
		metaQEventClient.setNamesrvAddr("192.168.2.173:9876");
		metaQEventClient.setInstanceName("manage");
		SendMqmsgListener listerner = new SendMqmsgListener();
		metaQEventClient.setMessageListener(listerner);
		listerner.setRocketOnMessageMap(new HashMap<>());
		Map<String, String> map = new HashMap<>();
		map.put("USDT_ACCOUNT_TOPIC1", "master");
		metaQEventClient.setSubscribeMap(map);
		try {
			metaQEventClient.init();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
}
