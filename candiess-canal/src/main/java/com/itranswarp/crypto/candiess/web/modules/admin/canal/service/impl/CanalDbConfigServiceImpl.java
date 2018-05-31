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
import com.itranswarp.crypto.candiess.web.modules.admin.canal.dao.CanalDbConfigDao;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.entity.CanalDbConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.service.CanalDbConfigService;


@Service("canalDbConfigService")
public class CanalDbConfigServiceImpl extends ServiceImpl<CanalDbConfigDao, CanalDbConfigEntity> implements CanalDbConfigService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CanalDbConfigEntity> page = this.selectPage(
                new Query<CanalDbConfigEntity>(params).getPage(),
                new EntityWrapper<CanalDbConfigEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(QueryViewVo<CanalDbConfigEntity> queryViewVo) {
		Page<CanalDbConfigEntity> page = this.selectPage(queryViewVo.getPageUtil(), queryViewVo.getWrapper());
		return new PageUtils(page);
	}

	@Override
	public List<CanalDbConfigEntity> download(QueryViewVo<CanalDbConfigEntity> queryViewVo) {
		List<CanalDbConfigEntity> list = this.selectList(queryViewVo.getWrapper());
		return list;
	}
}
