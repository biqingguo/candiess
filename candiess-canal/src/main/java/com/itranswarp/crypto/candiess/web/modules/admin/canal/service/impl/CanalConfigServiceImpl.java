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
import com.itranswarp.crypto.candiess.web.modules.admin.canal.dao.CanalConfigDao;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.entity.CanalConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.admin.canal.service.CanalConfigService;


@Service("canalConfigService")
public class CanalConfigServiceImpl extends ServiceImpl<CanalConfigDao, CanalConfigEntity> implements CanalConfigService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CanalConfigEntity> page = this.selectPage(
                new Query<CanalConfigEntity>(params).getPage(),
                new EntityWrapper<CanalConfigEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(QueryViewVo<CanalConfigEntity> queryViewVo) {
		Page<CanalConfigEntity> page = this.selectPage(queryViewVo.getPageUtil(), queryViewVo.getWrapper());
		return new PageUtils(page);
	}

	@Override
	public List<CanalConfigEntity> download(QueryViewVo<CanalConfigEntity> queryViewVo) {
		List<CanalConfigEntity> list = this.selectList(queryViewVo.getWrapper());
		return list;
	}
}
