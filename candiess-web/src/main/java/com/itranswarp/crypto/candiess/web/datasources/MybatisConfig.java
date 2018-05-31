package com.itranswarp.crypto.candiess.web.datasources;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;

/**
 * 配置多数据源
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017/8/19 0:41
 */
@Configuration
public class MybatisConfig {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Bean("commonDataSource")
	@ConfigurationProperties("spring.datasource.druid.common")
	public DataSource commonDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("exDataSource")
	@ConfigurationProperties("spring.datasource.druid.ex")
	public DataSource exDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("uiDataSource")
	@ConfigurationProperties("spring.datasource.druid.ui")
	public DataSource uiDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("mgDataSource")
	@ConfigurationProperties("spring.datasource.druid.mg")
	public DataSource mgDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean("adminDataSource")
	@ConfigurationProperties("spring.datasource.druid.admin")
	public DataSource adminDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@Primary
	public DataSource dataSource(DataSource commonDataSource, DataSource exDataSource, DataSource mgDataSource,
			DataSource uiDataSource, DataSource adminDataSource) {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceNames.COMMON.getValue(), commonDataSource);
		targetDataSources.put(DataSourceNames.EX.getValue(), exDataSource);
		targetDataSources.put(DataSourceNames.MG.getValue(), mgDataSource);
		targetDataSources.put(DataSourceNames.UI.getValue(), uiDataSource);
		targetDataSources.put(DataSourceNames.ADMIN.getValue(), adminDataSource);
		dynamicDataSource.setTargetDataSources(targetDataSources);
		dynamicDataSource.setDefaultTargetDataSource(adminDataSource);
		return dynamicDataSource;
	}

	@Bean("sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource, GlobalConfiguration globalConfiguration,
			MybatisConfiguration mybatisConfiguration, PaginationInterceptor paginationInterceptor) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
		mybatisSqlSessionFactoryBean.setDataSource(dataSource);
		mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfiguration);
		mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
		mybatisSqlSessionFactoryBean.setPlugins(new Interceptor[] { paginationInterceptor });
		try {
			mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
			return mybatisSqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			throw new RuntimeException(e);
		}
	}
}
