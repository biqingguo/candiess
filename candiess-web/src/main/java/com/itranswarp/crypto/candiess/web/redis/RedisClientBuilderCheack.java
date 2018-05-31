package com.itranswarp.crypto.candiess.web.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

public class RedisClientBuilderCheack {

	public static RedissonClient buildRedissionClient(RedisConfigurationManage rc) {
		Config config = new Config().setCodec(StringCodec.INSTANCE);
		if ("single".equals(rc.getMode())) {
			config.useSingleServer().setAddress(rc.getNodes().get(0));
			return Redisson.create(config);
		} else if ("cluster".equals(rc.getMode())) {
			ClusterServersConfig csc = config.useClusterServers();
			rc.getNodes().forEach(node -> {
				csc.addNodeAddress(node);
			});
			return Redisson.create(config);
		}
		throw new IllegalArgumentException("Invalid mode: " + rc.getMode());
	}
}
