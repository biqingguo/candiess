package com.itranswarp.crypto.candiess.web.modules.admin.canal;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.HAMode;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.IndexMode;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.MetaMode;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.SourcingType;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.StorageMode;
import com.itranswarp.crypto.CryptoManageApplication;
import com.itranswarp.crypto.manage.model.CanalDbConfig;
import com.itranswarp.crypto.manage.model.CanalPositionsConfig;
import com.itranswarp.crypto.manage.util.JsonUtil;
import com.itranswarp.warpdb.WarpDb;

@Component
public class CanalStart {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier(CryptoManageApplication.MANAGE_WARP_DB)
	protected WarpDb db;
	private Map<String, ManageCanalInstanceWithManager> map = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		List<CanalDbConfig> list = db.list("SELECT * FROM canalDbConfig WHERE `status` = ?", "TRUE");
		for (CanalDbConfig canalDbConfig : list) {
			List<CanalPositionsConfig> canalPositionsConfigList = db
					.list("SELECT * FROM canalPositionsConfig WHERE dbConfigId = ?", canalDbConfig.id);

			Canal canal = new Canal();
			canal.setId(canalDbConfig.salveId);
			canal.setName(canalDbConfig.destination);
			canal.setDesc(canalDbConfig.textValue);
			CanalParameter parameter = new CanalParameter();
			parameter.setMetaMode(MetaMode.MEMORY); // 冷备，可选择混合模式
			parameter.setHaMode(HAMode.HEARTBEAT);
			parameter.setIndexMode(IndexMode.META);// 内存版store，需要选择meta做为index

			parameter.setStorageMode(StorageMode.MEMORY);
			parameter.setMemoryStorageBufferSize(32 * 1024);

			parameter.setSourcingType(SourcingType.MYSQL);
			parameter.setDbAddresses(Arrays
					.asList(new InetSocketAddress(canalDbConfig.dbAddress, Integer.parseInt(canalDbConfig.dbPort))));
			parameter.setDbUsername(canalDbConfig.dnUser);
			parameter.setDbPassword(canalDbConfig.dbPassword);
			if (canalPositionsConfigList != null && !canalPositionsConfigList.isEmpty()) {
				CanalPositionsConfig canalPositionsConfig = canalPositionsConfigList.get(0);
				parameter.setPositions(Arrays.asList("{\"journalName\":\"" + canalPositionsConfig.journalName
						+ "\",\"position\":" + canalPositionsConfig.position + "L,\"timestamp\":"
						+ canalPositionsConfig.timestamp + "L}"));
			}
			parameter.setSlaveId(canalDbConfig.salveId);
			parameter.setDefaultConnectionTimeoutInSeconds(30);
			parameter.setConnectionCharset("UTF-8");
			parameter.setConnectionCharsetNumber((byte) 33);
			parameter.setReceiveBufferSize(8 * 1024);
			parameter.setSendBufferSize(8 * 1024);
			parameter.setDetectingEnable(true);
			parameter.setDetectingIntervalInSeconds(10);
			parameter.setDetectingRetryTimes(3);
			parameter.setDetectingSQL(canalDbConfig.detectingSql);
			// parameter.setMasterTimestamp(1524730141l);
			canal.setCanalParameter(parameter);
			ManageCanalInstanceWithManager canalInstanceWithManager = new ManageCanalInstanceWithManager(canal,
					canalDbConfig.destination, canalDbConfig.id);
			canalInstanceWithManager.start();
			map.put(canalDbConfig.destination, canalInstanceWithManager);
		}
		// ManageCanalInstanceWithManager canalInstanceWithManager = new
		// ManageCanalInstanceWithManager(buildCanal(),
		// DESTINATION);
		// canalInstanceWithManager.start();
		// map.put("manage", canalInstanceWithManager);
	}

	@PreDestroy
	public void destroy() {
		for (Entry<String, ManageCanalInstanceWithManager> entry : map.entrySet()) {
			entry.getValue().stop();
		}
	}

	// protected Canal buildCanal() {
	// Canal canal = new Canal();
	// canal.setId(1L);
	// canal.setName(DESTINATION);
	// canal.setDesc("测试");
	// CanalParameter parameter = new CanalParameter();
	// parameter.setMetaMode(MetaMode.MEMORY); // 冷备，可选择混合模式
	// parameter.setHaMode(HAMode.HEARTBEAT);
	// parameter.setIndexMode(IndexMode.META);// 内存版store，需要选择meta做为index
	//
	// parameter.setStorageMode(StorageMode.MEMORY);
	// parameter.setMemoryStorageBufferSize(32 * 1024);
	//
	// parameter.setSourcingType(SourcingType.MYSQL);
	// parameter.setDbAddresses(Arrays.asList(new
	// InetSocketAddress(MYSQL_ADDRESS, 3306)));
	// parameter.setDbUsername(USERNAME);
	// parameter.setDbPassword(PASSWORD);
	// parameter.setPositions(
	// Arrays.asList("{\"journalName\":\"mysql-bin2.000006\",\"position\":8928L,\"timestamp\":1524730132L}"));
	// parameter.setSlaveId(1234L);
	// parameter.setDefaultConnectionTimeoutInSeconds(30);
	// parameter.setConnectionCharset("UTF-8");
	// parameter.setConnectionCharsetNumber((byte) 33);
	// parameter.setReceiveBufferSize(8 * 1024);
	// parameter.setSendBufferSize(8 * 1024);
	// parameter.setDetectingEnable(true);
	// parameter.setDetectingIntervalInSeconds(10);
	// parameter.setDetectingRetryTimes(3);
	// parameter.setDetectingSQL(DETECTING_SQL);
	// // parameter.setMasterTimestamp(1524730141l);
	// canal.setCanalParameter(parameter);
	// return canal;
	// }
}
