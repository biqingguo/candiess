package com.itranswarp.crypto.candiess.web.modules.ui.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;
import com.itranswarp.crypto.candiess.web.modules.ui.test.entity.RafflePrizeUserSaveValueEntity;

import java.util.Map;
import java.util.List;
/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:04:23
 */
public interface RafflePrizeUserSaveValueService extends IService<RafflePrizeUserSaveValueEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(QueryViewVo<RafflePrizeUserSaveValueEntity> queryViewVo);

	List<RafflePrizeUserSaveValueEntity> download(QueryViewVo<RafflePrizeUserSaveValueEntity> queryViewVo);
}

