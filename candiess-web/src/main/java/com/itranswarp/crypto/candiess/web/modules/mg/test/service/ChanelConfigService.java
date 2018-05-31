package com.itranswarp.crypto.candiess.web.modules.mg.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;
import com.itranswarp.crypto.candiess.web.modules.mg.test.entity.ChanelConfigEntity;

import java.util.Map;
import java.util.List;
/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:05:42
 */
public interface ChanelConfigService extends IService<ChanelConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(QueryViewVo<ChanelConfigEntity> queryViewVo);

	List<ChanelConfigEntity> download(QueryViewVo<ChanelConfigEntity> queryViewVo);
}

