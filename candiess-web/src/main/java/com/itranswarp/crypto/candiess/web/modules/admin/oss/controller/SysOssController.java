/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.itranswarp.crypto.candiess.web.modules.admin.oss.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.itranswarp.crypto.candiess.common.exception.RRException;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.common.utils.R;
import com.itranswarp.crypto.candiess.web.modules.admin.oss.cloud.DiskService;
import com.itranswarp.crypto.candiess.web.modules.admin.oss.entity.SysOssEntity;
import com.itranswarp.crypto.candiess.web.modules.admin.oss.service.SysOssService;

/**
 * 文件上传
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 12:13:26
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
	@Autowired
	private SysOssService sysOssService;
	@Autowired
	private DiskService diskService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public R list(@RequestParam Map<String, Object> params) {
		PageUtils page = sysOssService.queryPage(params);

		return R.ok().put("page", page);
	}

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		// 上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = diskService.uploadSuffix(file.getBytes(), suffix);

		// 保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.insert(ossEntity);

		return R.ok().put("url", url);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody Long[] ids) {
		sysOssService.deleteBatchIds(Arrays.asList(ids));

		return R.ok();
	}

}
