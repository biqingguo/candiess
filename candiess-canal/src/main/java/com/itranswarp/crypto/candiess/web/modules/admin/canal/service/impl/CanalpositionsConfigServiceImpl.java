package com.itranswarp.crypto.candiess.web.modules.admin.canal.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.itranswarp.crypto.candiess.api.query.Query;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.dao.CanalpositionsConfigDao;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.entity.CanalpositionsConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.service.CanalpositionsConfigService;

@Service("canalpositionsConfigService")
public class CanalpositionsConfigServiceImpl extends ServiceImpl<CanalpositionsConfigDao, CanalpositionsConfigEntity>
		implements CanalpositionsConfigService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<CanalpositionsConfigEntity> page = this.selectPage(new Query<CanalpositionsConfigEntity>(params).getPage(),
				new EntityWrapper<CanalpositionsConfigEntity>());

		return new PageUtils(page);
	}

	@Override
	public PageUtils queryPage(QueryViewVo<CanalpositionsConfigEntity> queryViewVo) {
		Page<CanalpositionsConfigEntity> page = this.selectPage(queryViewVo.getPageUtil(), queryViewVo.getWrapper());
		return new PageUtils(page);
	}

	@Override
	public List<CanalpositionsConfigEntity> download(QueryViewVo<CanalpositionsConfigEntity> queryViewVo) {
		List<CanalpositionsConfigEntity> list = this.selectList(queryViewVo.getWrapper());
		return list;
	}
}
