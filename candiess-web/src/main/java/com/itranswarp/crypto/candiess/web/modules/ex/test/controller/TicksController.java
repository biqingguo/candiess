package com.itranswarp.crypto.candiess.web.modules.ex.test.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.itranswarp.crypto.candiess.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.itranswarp.crypto.candiess.api.query.QueryViewVo;
import com.itranswarp.crypto.candiess.common.utils.ExcelUtil;
import com.itranswarp.crypto.candiess.common.utils.ExcelUtil.WriteExcel;
import com.itranswarp.crypto.candiess.web.modules.ex.test.entity.TicksEntity;
import com.itranswarp.crypto.candiess.web.modules.ex.test.service.TicksService;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:07:11
 */
@RestController
@RequestMapping("test/ticks")
public class TicksController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TicksService ticksService;

/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("test:ticks:list")
	public R list(QueryViewVo<TicksEntity> queryViewVo) {
		PageUtils page = ticksService.queryPage(queryViewVo);
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
	@RequiresPermissions("test:ticks:download")
	public void download(HttpServletResponse response, QueryViewVo<TicksEntity> queryViewVo) {
		try {
			List<TicksEntity> download = ticksService.download(queryViewVo);
			XSSFWorkbook sfworkbook = new XSSFWorkbook();
			ExcelUtil excelUtil = new ExcelUtil();
			SXSSFWorkbook createWorkbook = excelUtil.createWorkbook(sfworkbook);
			WriteExcel<TicksEntity> writeexcel = new WriteExcel<TicksEntity>() {

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
									}

				@Override
				public void write(XSSFRow contentxfrow, TicksEntity t) {
											XSSFCell createCell1 = contentxfrow.createCell(0);
						createCell1.setCellValue(t.getSymbol());
											XSSFCell createCell2 = contentxfrow.createCell(1);
						createCell2.setCellValue(t.getTakerorderid());
											XSSFCell createCell3 = contentxfrow.createCell(2);
						createCell3.setCellValue(t.getMakerorderid());
											XSSFCell createCell4 = contentxfrow.createCell(3);
						createCell4.setCellValue(t.getPrice().toString());
											XSSFCell createCell5 = contentxfrow.createCell(4);
						createCell5.setCellValue(t.getAmount().toString());
											XSSFCell createCell6 = contentxfrow.createCell(5);
						createCell6.setCellValue(t.getId());
											XSSFCell createCell7 = contentxfrow.createCell(6);
						createCell7.setCellValue(t.getCreatedat());
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
    @RequiresPermissions("test:ticks:info")
    public R info(@PathVariable("id") Long id){
        TicksEntity ticks = ticksService.selectById(id);

        return R.ok().put("ticks", ticks);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("test:ticks:save")
    public R save(@RequestBody TicksEntity ticks){
        ticksService.insert(ticks);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("test:ticks:update")
    public R update(@RequestBody TicksEntity ticks){
        ValidatorUtils.validateEntity(ticks);
        ticksService.updateAllColumnById(ticks);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("test:ticks:delete")
    public R delete(@RequestBody Long[] ids){
        ticksService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
