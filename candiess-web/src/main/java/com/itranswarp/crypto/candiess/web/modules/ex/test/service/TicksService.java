package com.itranswarp.crypto.candiess.web.modules.ex.test.service;

import com.baomidou.mybatisplus.service.IService;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;
import com.itranswarp.crypto.candiess.web.modules.ex.test.entity.TicksEntity;

import java.util.Map;
import java.util.List;
/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:07:11
 */
public interface TicksService extends IService<TicksEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(QueryViewVo<TicksEntity> queryViewVo);

	List<TicksEntity> download(QueryViewVo<TicksEntity> queryViewVo);
}

