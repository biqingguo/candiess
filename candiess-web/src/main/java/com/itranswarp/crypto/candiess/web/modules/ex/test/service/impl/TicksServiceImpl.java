package com.itranswarp.crypto.candiess.web.modules.ex.test.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.web.common.utils.Query;

import com.itranswarp.crypto.candiess.web.modules.ex.test.dao.TicksDao;
import com.itranswarp.crypto.candiess.web.modules.ex.test.entity.TicksEntity;
import com.itranswarp.crypto.candiess.web.modules.ex.test.service.TicksService;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;


@Service("ticksService")
public class TicksServiceImpl extends ServiceImpl<TicksDao, TicksEntity> implements TicksService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TicksEntity> page = this.selectPage(
                new Query<TicksEntity>(params).getPage(),
                new EntityWrapper<TicksEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(QueryViewVo<TicksEntity> queryViewVo) {
		Page<TicksEntity> page = this.selectPage(queryViewVo.getPageUtil(), queryViewVo.getWrapper());
		return new PageUtils(page);
	}

	@Override
	public List<TicksEntity> download(QueryViewVo<TicksEntity> queryViewVo) {
		List<TicksEntity> list = this.selectList(queryViewVo.getWrapper());
		return list;
	}
}
