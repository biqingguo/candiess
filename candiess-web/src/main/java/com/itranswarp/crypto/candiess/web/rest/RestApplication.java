package com.itranswarp.crypto.candiess.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestApplication extends SpringBootServletInitializer {
	@Value("${crypto.manage.api.endpoint}")
	String apiEndpoint;

	@Value("${crypto.manage.api.api-key}")
	String apiKey;

	@Value("${crypto.manage.api.api-secret}")
	String apiSecret;

	@Value("${crypto.manage.ui.endpoint}")
	String uiEndpoint;

	@Value("${crypto.manage.ui.api-key}")
	String uiKey;

	@Value("${crypto.manage.ui.api-secret}")
	String uiSecret;

//	@Primary
//	@Bean
//	public RestClient createRestClientForApi() {
//		return new RestClient.Builder(apiEndpoint).authenticate(apiKey, apiSecret).build();
//	}
//
//	/**
//	 * Create a rest client.
//	 * 
//	 * @return RestClient object.
//	 */
//	@Bean("uiRestClient")
//	public RestClient createRestClientForUI() {
//		return new RestClient.Builder(uiEndpoint).authenticate(uiKey, uiSecret).build();
//	}
}
