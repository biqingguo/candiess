package com.itranswarp.crypto.candiess.web.modules.ui.test.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:04:23
 */
@TableName("raffle_prize_user_save_value")
public class RafflePrizeUserSaveValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long raffleprizeuserid;
	/**
	 * 
	 */
	private String propertykeys;
	/**
	 * 
	 */
	private String propertykeyvalue;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 
	 */
	private Long sortsid;
	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long createdat;
	/**
	 * 
	 */
	private Long updatedat;
	/**
	 * 
	 */
	private Long version;
	/**
	 * 
	 */
	private String prizetype;

	/**
	 * 设置：
	 */
	public void setRaffleprizeuserid(Long raffleprizeuserid) {
		this.raffleprizeuserid = raffleprizeuserid;
	}
	/**
	 * 获取：
	 */
	public Long getRaffleprizeuserid() {
		return raffleprizeuserid;
	}
	/**
	 * 设置：
	 */
	public void setPropertykeys(String propertykeys) {
		this.propertykeys = propertykeys;
	}
	/**
	 * 获取：
	 */
	public String getPropertykeys() {
		return propertykeys;
	}
	/**
	 * 设置：
	 */
	public void setPropertykeyvalue(String propertykeyvalue) {
		this.propertykeyvalue = propertykeyvalue;
	}
	/**
	 * 获取：
	 */
	public String getPropertykeyvalue() {
		return propertykeyvalue;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：
	 */
	public void setSortsid(Long sortsid) {
		this.sortsid = sortsid;
	}
	/**
	 * 获取：
	 */
	public Long getSortsid() {
		return sortsid;
	}
	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setCreatedat(Long createdat) {
		this.createdat = createdat;
	}
	/**
	 * 获取：
	 */
	public Long getCreatedat() {
		return createdat;
	}
	/**
	 * 设置：
	 */
	public void setUpdatedat(Long updatedat) {
		this.updatedat = updatedat;
	}
	/**
	 * 获取：
	 */
	public Long getUpdatedat() {
		return updatedat;
	}
	/**
	 * 设置：
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	/**
	 * 获取：
	 */
	public Long getVersion() {
		return version;
	}
	/**
	 * 设置：
	 */
	public void setPrizetype(String prizetype) {
		this.prizetype = prizetype;
	}
	/**
	 * 获取：
	 */
	public String getPrizetype() {
		return prizetype;
	}
}
