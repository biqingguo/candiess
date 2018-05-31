package com.itranswarp.crypto.candiess.mq.biz;

import java.util.List;
import java.util.Map;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import com.itranswarp.crypto.candiess.mq.annotation.RocketOnMessage;

public class SendMqmsgListener implements MessageListenerConcurrently {

	/**
	 * bean map 保存需要调用的bean集合
	 */
	Map<String/* topic */, Map<String/* tag */, RocketOnMessage>> rocketOnMessageMap;

	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		// 目前metaQ客户端每次列表只提供一个消费对象
		Boolean result = true;
		ConsumeConcurrentlyStatus consumeConcurrentlyStatus = ConsumeConcurrentlyStatus.RECONSUME_LATER;
		for (MessageExt msg : msgs) {
			ConsumeConcurrentlyStatus onMessage = null;
			try {
				String topic = msg.getTopic();
				String tags = msg.getTags();
				if (rocketOnMessageMap.containsKey(topic)) {
					Map<String, RocketOnMessage> tagMap = rocketOnMessageMap.get(topic.trim());
					if (tagMap.containsKey(tags)) {
						RocketOnMessage rocketOnMessage = tagMap.get(tags);
						consumeConcurrentlyStatus = rocketOnMessage.onMessage(msg);
					}
					if (tagMap.containsKey("*")) {
						RocketOnMessage rocketOnMessage = tagMap.get("*");
						consumeConcurrentlyStatus = rocketOnMessage.onMessage(msg);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				result = false;
			} finally {
				result = result & consumeConcurrentlyStatus.equals(ConsumeConcurrentlyStatus.CONSUME_SUCCESS);
			}
		}
		if (result) {
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		} else {
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
	}

	public Map<String, Map<String, RocketOnMessage>> getRocketOnMessageMap() {
		return rocketOnMessageMap;
	}

	public void setRocketOnMessageMap(Map<String, Map<String, RocketOnMessage>> rocketOnMessageMap) {
		this.rocketOnMessageMap = rocketOnMessageMap;
	}

}
