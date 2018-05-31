package com.itranswarp.crypto.candiess.web.modules.mg.test.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.crypto.candiess.api.query.QueryViewVo;
import com.itranswarp.crypto.candiess.common.utils.ExcelUtil;
import com.itranswarp.crypto.candiess.common.utils.ExcelUtil.WriteExcel;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.common.utils.R;
import com.itranswarp.crypto.candiess.common.validator.ValidatorUtils;
import com.itranswarp.crypto.candiess.web.modules.mg.test.entity.ChanelConfigEntity;
import com.itranswarp.crypto.candiess.web.modules.mg.test.service.ChanelConfigService;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:05:42
 */
@RestController
@RequestMapping("test/chanelconfig")
public class ChanelConfigController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ChanelConfigService chanelConfigService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("test:chanelconfig:list")
	public R list(QueryViewVo<ChanelConfigEntity> queryViewVo) {
		PageUtils page = chanelConfigService.queryPage(queryViewVo);
		return R.ok().put("page", page);
	}

	/**
	 * 导出数据到excel
	 * 
	 * @param response
	 * @param queryViewVo
	 * @return
	 */
	@RequestMapping("/download")
	@RequiresPermissions("test:chanelconfig:download")
	public void download(HttpServletResponse response, QueryViewVo<ChanelConfigEntity> queryViewVo) {
		try {
			List<ChanelConfigEntity> download = chanelConfigService.download(queryViewVo);
			XSSFWorkbook sfworkbook = new XSSFWorkbook();
			ExcelUtil excelUtil = new ExcelUtil();
			SXSSFWorkbook createWorkbook = excelUtil.createWorkbook(sfworkbook);
			WriteExcel<ChanelConfigEntity> writeexcel = new WriteExcel<ChanelConfigEntity>() {

				@Override
				public void writeHead(XSSFRow contentxfrow) {
					XSSFCell createCell1 = contentxfrow.createCell(0);
					createCell1.setCellValue("");
					XSSFCell createCell2 = contentxfrow.createCell(1);
					createCell2.setCellValue("");
					XSSFCell createCell3 = contentxfrow.createCell(2);
					createCell3.setCellValue("");
					XSSFCell createCell4 = contentxfrow.createCell(3);
					createCell4.setCellValue("");
					XSSFCell createCell5 = contentxfrow.createCell(4);
					createCell5.setCellValue("");
					XSSFCell createCell6 = contentxfrow.createCell(5);
					createCell6.setCellValue("");
					XSSFCell createCell7 = contentxfrow.createCell(6);
					createCell7.setCellValue("");
					XSSFCell createCell8 = contentxfrow.createCell(7);
					createCell8.setCellValue("");
				}

				@Override
				public void write(XSSFRow contentxfrow, ChanelConfigEntity t) {
					XSSFCell createCell1 = contentxfrow.createCell(0);
					createCell1.setCellValue(t.getChanel());
					XSSFCell createCell2 = contentxfrow.createCell(1);
					createCell2.setCellValue(t.getStatus());
					XSSFCell createCell3 = contentxfrow.createCell(2);
					createCell3.setCellValue(t.getUserid());
					XSSFCell createCell4 = contentxfrow.createCell(3);
					createCell4.setCellValue(t.getRemark());
					XSSFCell createCell5 = contentxfrow.createCell(4);
					createCell5.setCellValue(t.getId());
					XSSFCell createCell6 = contentxfrow.createCell(5);
					createCell6.setCellValue(t.getCreatedat());
					XSSFCell createCell7 = contentxfrow.createCell(6);
					createCell7.setCellValue(t.getUpdatedat());
					XSSFCell createCell8 = contentxfrow.createCell(7);
					createCell8.setCellValue(t.getVersion());
				}
			};
			excelUtil.createHead(sfworkbook, createWorkbook, writeexcel);
			excelUtil.writeXlsFile(sfworkbook, createWorkbook, download, writeexcel);
			/**
			 * 保存文件
			 */
			String saveXlsFile = excelUtil.saveXlsFile(sfworkbook, queryViewVo.getPassword());
			excelUtil.sendExcel(response, saveXlsFile);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("test:chanelconfig:info")
	public R info(@PathVariable("id") Long id) {
		ChanelConfigEntity chanelConfig = chanelConfigService.selectById(id);

		return R.ok().put("chanelConfig", chanelConfig);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("test:chanelconfig:save")
	public R save(@RequestBody ChanelConfigEntity chanelConfig) {
		chanelConfigService.insert(chanelConfig);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("test:chanelconfig:update")
	public R update(@RequestBody ChanelConfigEntity chanelConfig) {
		ValidatorUtils.validateEntity(chanelConfig);
		chanelConfigService.updateAllColumnById(chanelConfig);// 全部更新

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("test:chanelconfig:delete")
	public R delete(@RequestBody Long[] ids) {
		chanelConfigService.deleteBatchIds(Arrays.asList(ids));

		return R.ok();
	}

}
