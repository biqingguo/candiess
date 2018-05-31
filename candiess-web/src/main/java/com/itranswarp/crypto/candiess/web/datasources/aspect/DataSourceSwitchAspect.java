package com.itranswarp.crypto.candiess.web.datasources.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.itranswarp.crypto.candiess.web.datasources.DataSourceNames;
import com.itranswarp.crypto.candiess.web.datasources.DbContextHolder;

@Component
@Aspect
@Order(-100) // 这是为了保证AOP在事务注解之前生效,Order的值越小,优先级越高
public class DataSourceSwitchAspect {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Pointcut("execution(* com.itranswarp.crypto.candiess.web.modules.ex..*.*(..))")
	private void exAspect() {
	}

	@Pointcut("execution(* com.itranswarp.crypto.candiess.web.modules.mg..*.*(..))")
	private void mgAspect() {
	}

	@Pointcut("execution(* com.itranswarp.crypto.candiess.web.modules.ui..*.*(..))")
	private void uiAspect() {
	}

	@Pointcut("execution(* com.itranswarp.crypto.candiess.web.modules.admin..*.*(..))")
	private void admin1Aspect() {
	}

	@Before("exAspect()")
	public void exDb() {
		logger.info("切换到ex 数据源...");
		DbContextHolder.setDbType(DataSourceNames.EX);
	}

	@Before("mgAspect()")
	public void mgDb() {
		logger.info("切换到mg 数据源...");
		DbContextHolder.setDbType(DataSourceNames.MG);
	}

	@Before("uiAspect()")
	public void uiDb() {
		logger.info("切换到ui 数据源...");
		DbContextHolder.setDbType(DataSourceNames.UI);
	}

	@Before("admin1Aspect()")
	public void adminDb() {
		logger.info("切换到admin 数据源...");
		DbContextHolder.setDbType(DataSourceNames.ADMIN);
	}
}
