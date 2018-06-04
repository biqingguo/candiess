package com.itranswarp.crypto.candiess.web.modules.admin.canal.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-31 22:17:36
 */
@TableName("canal_tb_config")
public class CanalConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long dbconfigid;
	/**
	 * 
	 */
	private String dbname;
	/**
	 * 
	 */
	private String tbname;
	/**
	 * 
	 */
	private String mqqueue;
	/**
	 * 
	 */
	private String textvalue;
	/**
	 * 
	 */
	private String status;
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
	public void setDbconfigid(Long dbconfigid) {
		this.dbconfigid = dbconfigid;
	}
	/**
	 * 获取：
	 */
	public Long getDbconfigid() {
		return dbconfigid;
	}
	/**
	 * 设置：
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	/**
	 * 获取：
	 */
	public String getDbname() {
		return dbname;
	}
	/**
	 * 设置：
	 */
	public void setTbname(String tbname) {
		this.tbname = tbname;
	}
	/**
	 * 获取：
	 */
	public String getTbname() {
		return tbname;
	}
	/**
	 * 设置：
	 */
	public void setMqqueue(String mqqueue) {
		this.mqqueue = mqqueue;
	}
	/**
	 * 获取：
	 */
	public String getMqqueue() {
		return mqqueue;
	}
	/**
	 * 设置：
	 */
	public void setTextvalue(String textvalue) {
		this.textvalue = textvalue;
	}
	/**
	 * 获取：
	 */
	public String getTextvalue() {
		return textvalue;
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
