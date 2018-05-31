/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package com.itranswarp.crypto.candiess.mq.consumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * metaQ消费者客户端接口
 * 
 * @author heng.liu
 * @version $Id: MetaQConsumerClient.java, v 0.1 Sep 29, 2013 12:09:38 PM
 *          heng.liu Exp $
 */
public interface MetaQConsumerClient {

	/**
	 * 暂停metaQ消费器
	 * 
	 * @throws Exception
	 */
	public void suspendConsumer();

	/**
	 * 继续metaQ消费器
	 * 
	 * @throws Exception
	 */
	public void resumeConsumer();

	/**
	 * 修改metaQ消费线程数
	 * 
	 * @throws Exception
	 */
	public void updateConsumerCorePoolSize(int corePoolSize);

	/**
	 * 根据msg id 获得metaq 消息
	 * 
	 * @param msgId
	 * @return MessageExt
	 * @throws Exception
	 */
	public MessageExt viewMessage(String msgId) throws Exception;

	/**
	 * 关闭订阅端
	 */
	public void shown();

	/**
	 * 开启订阅端
	 * 
	 * @throws MQClientException
	 */
	public void init() throws MQClientException;

}