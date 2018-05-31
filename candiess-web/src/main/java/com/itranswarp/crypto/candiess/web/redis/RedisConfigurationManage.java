package com.itranswarp.crypto.candiess.web.redis;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis configuration used for ui.
 * 
 * @author liwenyu
 */
@Component
@ConfigurationProperties("crypto.manage.redis")
public class RedisConfigurationManage {

	private String mode = "single";
	private List<String> nodes;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

}
