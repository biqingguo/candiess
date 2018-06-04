package com.itranswarp.crypto.candiess.web.modules.admin.canal;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.otter.canal.instance.manager.model.Canal;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.HAMode;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.IndexMode;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.MetaMode;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.SourcingType;
import com.alibaba.otter.canal.instance.manager.model.CanalParameter.StorageMode;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.entity.CanalDbConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.entity.CanalpositionsConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.service.CanalDbConfigService;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.service.CanalpositionsConfigService;

@Component
public class CanalStart {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CanalDbConfigService canalDbConfigService;
	@Autowired
	private CanalpositionsConfigService canalpositionsConfigService;

	private Map<String, ManageCanalInstanceWithManager> map = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {

		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("status", "TRUE");
		List<CanalDbConfigEntity> selectByMap = canalDbConfigService.selectByMap(columnMap);
		for (CanalDbConfigEntity canalDbConfig : selectByMap) {
			Map<String, Object> columnMaps = new HashMap<String, Object>();
			columnMaps.put("dbConfigId", canalDbConfig.getId());
			Wrapper<CanalpositionsConfigEntity> wrapper = new EntityWrapper<>();
			wrapper.allEq(columnMaps);
			CanalpositionsConfigEntity selectOne = canalpositionsConfigService.selectOne(wrapper);
			Canal canal = new Canal();
			canal.setId(canalDbConfig.getSalveid());
			canal.setName(canalDbConfig.getDestination());
			canal.setDesc(canalDbConfig.getTextvalue());
			CanalParameter parameter = new CanalParameter();
			parameter.setMetaMode(MetaMode.MEMORY); // 冷备，可选择混合模式
			parameter.setHaMode(HAMode.HEARTBEAT);
			parameter.setIndexMode(IndexMode.META);// 内存版store，需要选择meta做为index

			parameter.setStorageMode(StorageMode.MEMORY);
			parameter.setMemoryStorageBufferSize(32 * 1024);

			parameter.setSourcingType(SourcingType.MYSQL);
			parameter.setDbAddresses(Arrays.asList(
					new InetSocketAddress(canalDbConfig.getDbaddress(), Integer.parseInt(canalDbConfig.getDbport()))));
			parameter.setDbUsername(canalDbConfig.getDnuser());
			parameter.setDbPassword(canalDbConfig.getDbpassword());
			if (selectOne != null) {
				parameter.setPositions(
						Arrays.asList("{\"journalName\":\"" + selectOne.getJournalname() + "\",\"position\":"
								+ selectOne.getPosition() + "L,\"timestamp\":" + selectOne.getTimestamp() + "L}"));
			}
			parameter.setSlaveId(canalDbConfig.getSalveid());
			parameter.setDefaultConnectionTimeoutInSeconds(30);
			parameter.setConnectionCharset("UTF-8");
			parameter.setConnectionCharsetNumber((byte) 33);
			parameter.setReceiveBufferSize(8 * 1024);
			parameter.setSendBufferSize(8 * 1024);
			parameter.setDetectingEnable(true);
			parameter.setDetectingIntervalInSeconds(10);
			parameter.setDetectingRetryTimes(3);
			parameter.setDetectingSQL(canalDbConfig.getDetectingsql());
			// parameter.setMasterTimestamp(1524730141l);
			canal.setCanalParameter(parameter);
			ManageCanalInstanceWithManager canalInstanceWithManager = new ManageCanalInstanceWithManager(canal,
					canalDbConfig.getDestination(), canalDbConfig.getId());
			canalInstanceWithManager.start();
			map.put(canalDbConfig.getDestination(), canalInstanceWithManager);
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
