package com.itranswarp.crypto.candiess.mq.producer;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.itranswarp.crypto.candiess.mq.SerializeUtil;
import com.itranswarp.crypto.candiess.mq.api.MsgSendResult;
import com.itranswarp.crypto.candiess.mq.api.MsgSendResultCode;

/**
 * metaq消息发布者
 * 
 * @author jin.qian
 * @version $Id: MetaqMessagePublisherImpl.java, v 0.1 2015年4月14日 下午1:56:09
 *          jin.qian Exp $
 */
public class MetaqMessagePublisherImpl implements MetaqMessagePublisher {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(MetaqMessagePublisherImpl.class);

	/** 生产者 */
	private DefaultMQProducer producer;

	/** MetaQ 相关参数配置 */
	private String groupId;

	private String namesrvaddr;

	/**
	 * 初始化方法
	 */
	// @PostConstruct
	public void init() {
		// 设置 nameserver 地址
		if (producer == null) {
			/*
			 * 设置全局 静态变量
			 */
			System.setProperty("rocketmq.namesrv.domain", namesrvaddr);
			producer = new DefaultMQProducer(groupId);
			producer.setNamesrvAddr(namesrvaddr);
		}
		try {
			producer.start();
		} catch (MQClientException e) {
			logger.error("metaQ producer start failed!", e);
		}
	}

	public MsgSendResult sendMessage(Message message) {
		try {
			SendResult res = producer.send(message);
			if (res == null) {
				return MsgSendResult.genFailedResult(MsgSendResultCode.SEND_FAILED.name());
			}
			if (logger.isDebugEnabled()) {
				logger.debug(getDigestString(res, message));
			}
			if (SendStatus.SEND_OK == res.getSendStatus()) {
				return MsgSendResult.genSuccessResult(res.getMsgId());
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getMessage(e));
		}
		return MsgSendResult.genFailedResult(MsgSendResultCode.SEND_FAILED.name());
	}

	/**
	 * 结果日志
	 * 
	 * @param res
	 * @param message
	 * @return
	 */
	private String getDigestString(SendResult res, Message message) {
		StringBuffer sb = new StringBuffer();
		sb.append("MQ SendResult:");
		sb.append("(");
		sb.append(res.getSendStatus()).append(",");
		sb.append(res.getMsgId()).append(",");
		sb.append(message.getTopic()).append(",");
		sb.append(message.getTags()).append(",");
		if (res.getMessageQueue() != null) {
			sb.append(res.getMessageQueue().getBrokerName()).append(",");
			sb.append(res.getMessageQueue().getQueueId()).append(",");
		}
		sb.append(res.getQueueOffset());
		sb.append(")");

		return sb.toString();
	}

	public <T extends Serializable> MsgSendResult sendMessage(String topic, String tag, T payload,
			Map<String, String> properties) throws IOException {

		Message msg = null;

		// 序列化主对象
		byte[] byteArray = SerializeUtil.serialize(payload);
		if (byteArray == null) {
			return MsgSendResult.genFailedResult(MsgSendResultCode.SERIALIZABLE_FAILED.name());
		}

		// 构造message
		msg = new Message(topic, byteArray);

		// 加入扩展信息
		if (!CollectionUtils.isEmpty(properties)) {
			Map<String, String> currentProperties = msg.getProperties();
			currentProperties.putAll(properties);
		}

		msg.setTags(tag);
		MsgSendResult res = this.sendMessage(msg);
		System.out.println("MsgSendResult :"+res);
		return res;
	}
	//
	// public static void main(String[] args) throws MQClientException,
	// IOException {
	// MetaqMessagePublisherImpl producer = new MetaqMessagePublisherImpl();
	// producer.init();
	// System.out.println("start MetaQEventClient ");
	// Object bom = new Object();
	// for (int i = 1; i < 10; i++) {
	// MsgSendResult sendResult1 = producer.sendMessage("T_bonus_order111",
	// "bonus_cash111", JSONObject.toJSONString(bom), null);
	// System.out.println(sendResult1);
	// }
	// producer.destroy();
	// System.out.println("test end");
	//
	// }

	/**
	 * destroy
	 */
	public void destroy() {
		if (producer != null) {
			producer.shutdown();
		}
	}

	/**
	 * setProducer
	 * 
	 * @param producer
	 */
	public void setProducer(DefaultMQProducer producer) {
		this.producer = producer;
	}

	/**
	 * setGroupId
	 * 
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getNamesrvaddr() {
		return namesrvaddr;
	}

	public void setNamesrvaddr(String namesrvaddr) {
		this.namesrvaddr = namesrvaddr;
	}

	public String getGroupId() {
		return groupId;
	}

	public static void main(String[] args) {
		MetaqMessagePublisherImpl publisher = new MetaqMessagePublisherImpl();
		publisher.setNamesrvaddr("192.168.2.173:9876");
		publisher.setGroupId("manage");
		publisher.init();
		Map properties = new HashMap<String, String>();
		try {
			// 队列名称、标签、消息主体、自定义属性
			String substring = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
			System.out.println(substring);
			publisher.sendMessage("USDT_ACCOUNT_TOPIC1", "master", substring, properties);
			publisher.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
