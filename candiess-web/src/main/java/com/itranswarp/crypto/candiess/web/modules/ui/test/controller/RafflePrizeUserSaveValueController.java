package com.itranswarp.crypto.candiess.web.modules.ui.test.controller;

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
import com.itranswarp.crypto.candiess.web.modules.ui.test.entity.RafflePrizeUserSaveValueEntity;
import com.itranswarp.crypto.candiess.web.modules.ui.test.service.RafflePrizeUserSaveValueService;
import com.itranswarp.crypto.candiess.common.utils.PageUtils;
import com.itranswarp.crypto.candiess.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:04:23
 */
@RestController
@RequestMapping("test/raffleprizeusersavevalue")
public class RafflePrizeUserSaveValueController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RafflePrizeUserSaveValueService rafflePrizeUserSaveValueService;

/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("test:raffleprizeusersavevalue:list")
	public R list(QueryViewVo<RafflePrizeUserSaveValueEntity> queryViewVo) {
		PageUtils page = rafflePrizeUserSaveValueService.queryPage(queryViewVo);
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
	@RequiresPermissions("test:raffleprizeusersavevalue:download")
	public void download(HttpServletResponse response, QueryViewVo<RafflePrizeUserSaveValueEntity> queryViewVo) {
		try {
			List<RafflePrizeUserSaveValueEntity> download = rafflePrizeUserSaveValueService.download(queryViewVo);
			XSSFWorkbook sfworkbook = new XSSFWorkbook();
			ExcelUtil excelUtil = new ExcelUtil();
			SXSSFWorkbook createWorkbook = excelUtil.createWorkbook(sfworkbook);
			WriteExcel<RafflePrizeUserSaveValueEntity> writeexcel = new WriteExcel<RafflePrizeUserSaveValueEntity>() {

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
											XSSFCell createCell9 = contentxfrow.createCell(8);
						createCell9.setCellValue("");
											XSSFCell createCell10 = contentxfrow.createCell(9);
						createCell10.setCellValue("");
									}

				@Override
				public void write(XSSFRow contentxfrow, RafflePrizeUserSaveValueEntity t) {
											XSSFCell createCell1 = contentxfrow.createCell(0);
						createCell1.setCellValue(t.getRaffleprizeuserid());
											XSSFCell createCell2 = contentxfrow.createCell(1);
						createCell2.setCellValue(t.getPropertykeys());
											XSSFCell createCell3 = contentxfrow.createCell(2);
						createCell3.setCellValue(t.getPropertykeyvalue());
											XSSFCell createCell4 = contentxfrow.createCell(3);
						createCell4.setCellValue(t.getRemark());
											XSSFCell createCell5 = contentxfrow.createCell(4);
						createCell5.setCellValue(t.getSortsid());
											XSSFCell createCell6 = contentxfrow.createCell(5);
						createCell6.setCellValue(t.getId());
											XSSFCell createCell7 = contentxfrow.createCell(6);
						createCell7.setCellValue(t.getCreatedat());
											XSSFCell createCell8 = contentxfrow.createCell(7);
						createCell8.setCellValue(t.getUpdatedat());
											XSSFCell createCell9 = contentxfrow.createCell(8);
						createCell9.setCellValue(t.getVersion());
											XSSFCell createCell10 = contentxfrow.createCell(9);
						createCell10.setCellValue(t.getPrizetype());
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
    @RequiresPermissions("test:raffleprizeusersavevalue:info")
    public R info(@PathVariable("id") Long id){
        RafflePrizeUserSaveValueEntity rafflePrizeUserSaveValue = rafflePrizeUserSaveValueService.selectById(id);

        return R.ok().put("rafflePrizeUserSaveValue", rafflePrizeUserSaveValue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("test:raffleprizeusersavevalue:save")
    public R save(@RequestBody RafflePrizeUserSaveValueEntity rafflePrizeUserSaveValue){
        rafflePrizeUserSaveValueService.insert(rafflePrizeUserSaveValue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("test:raffleprizeusersavevalue:update")
    public R update(@RequestBody RafflePrizeUserSaveValueEntity rafflePrizeUserSaveValue){
        ValidatorUtils.validateEntity(rafflePrizeUserSaveValue);
        rafflePrizeUserSaveValueService.updateAllColumnById(rafflePrizeUserSaveValue);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("test:raffleprizeusersavevalue:delete")
    public R delete(@RequestBody Long[] ids){
        rafflePrizeUserSaveValueService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
