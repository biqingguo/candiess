package com.itranswarp.crypto.candiess.web.modules.mg.test.service.impl;

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

import com.itranswarp.crypto.candiess.web.modules.mg.test.dao.ChanelConfigDao;
import com.itranswarp.crypto.candiess.web.modules.mg.test.entity.ChanelConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.mg.test.service.ChanelConfigService;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;


@Service("chanelConfigService")
public class ChanelConfigServiceImpl extends ServiceImpl<ChanelConfigDao, ChanelConfigEntity> implements ChanelConfigService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ChanelConfigEntity> page = this.selectPage(
                new Query<ChanelConfigEntity>(params).getPage(),
                new EntityWrapper<ChanelConfigEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(QueryViewVo<ChanelConfigEntity> queryViewVo) {
		Page<ChanelConfigEntity> page = this.selectPage(queryViewVo.getPageUtil(), queryViewVo.getWrapper());
		return new PageUtils(page);
	}

	@Override
	public List<ChanelConfigEntity> download(QueryViewVo<ChanelConfigEntity> queryViewVo) {
		List<ChanelConfigEntity> list = this.selectList(queryViewVo.getWrapper());
		return list;
	}
}
