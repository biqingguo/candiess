package com.itranswarp.crypto.candiess.web.modules.mg.test.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:05:42
 */
@TableName("chanel_config")
public class ChanelConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String chanel;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private Long userid;
	/**
	 * 
	 */
	private String remark;
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
	 * 设置：
	 */
	public void setChanel(String chanel) {
		this.chanel = chanel;
	}
	/**
	 * 获取：
	 */
	public String getChanel() {
		return chanel;
	}
	/**
	 * 设置：
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：
	 */
	public Long getUserid() {
		return userid;
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
}
