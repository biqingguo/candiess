package com.itranswarp.crypto.candiess.mq.annotation;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.MessageExt;

public interface RocketOnMessage {

	ConsumeConcurrentlyStatus onMessage(MessageExt messageExt);
}
