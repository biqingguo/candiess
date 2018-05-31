package com.itranswarp.crypto.candiess.web.modules.ui.test.service.impl;

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

import com.itranswarp.crypto.candiess.web.modules.ui.test.dao.RafflePrizeUserSaveValueDao;
import com.itranswarp.crypto.candiess.web.modules.ui.test.entity.RafflePrizeUserSaveValueEntity;
import com.itranswarp.crypto.candiess.web.modules.ui.test.service.RafflePrizeUserSaveValueService;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;


@Service("rafflePrizeUserSaveValueService")
public class RafflePrizeUserSaveValueServiceImpl extends ServiceImpl<RafflePrizeUserSaveValueDao, RafflePrizeUserSaveValueEntity> implements RafflePrizeUserSaveValueService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<RafflePrizeUserSaveValueEntity> page = this.selectPage(
                new Query<RafflePrizeUserSaveValueEntity>(params).getPage(),
                new EntityWrapper<RafflePrizeUserSaveValueEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryPage(QueryViewVo<RafflePrizeUserSaveValueEntity> queryViewVo) {
		Page<RafflePrizeUserSaveValueEntity> page = this.selectPage(queryViewVo.getPageUtil(), queryViewVo.getWrapper());
		return new PageUtils(page);
	}

	@Override
	public List<RafflePrizeUserSaveValueEntity> download(QueryViewVo<RafflePrizeUserSaveValueEntity> queryViewVo) {
		List<RafflePrizeUserSaveValueEntity> list = this.selectList(queryViewVo.getWrapper());
		return list;
	}
}
