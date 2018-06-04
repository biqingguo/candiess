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
@TableName("canal_db_config")
public class CanalDbConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String destination;
	/**
	 * 
	 */
	private Long salveid;
	/**
	 * 
	 */
	private String dbaddress;
	/**
	 * 
	 */
	private String dbport;
	/**
	 * 
	 */
	private String dnuser;
	/**
	 * 
	 */
	private String dbpassword;
	/**
	 * 
	 */
	private String detectingsql;
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
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * 获取：
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * 设置：
	 */
	public void setSalveid(Long salveid) {
		this.salveid = salveid;
	}
	/**
	 * 获取：
	 */
	public Long getSalveid() {
		return salveid;
	}
	/**
	 * 设置：
	 */
	public void setDbaddress(String dbaddress) {
		this.dbaddress = dbaddress;
	}
	/**
	 * 获取：
	 */
	public String getDbaddress() {
		return dbaddress;
	}
	/**
	 * 设置：
	 */
	public void setDbport(String dbport) {
		this.dbport = dbport;
	}
	/**
	 * 获取：
	 */
	public String getDbport() {
		return dbport;
	}
	/**
	 * 设置：
	 */
	public void setDnuser(String dnuser) {
		this.dnuser = dnuser;
	}
	/**
	 * 获取：
	 */
	public String getDnuser() {
		return dnuser;
	}
	/**
	 * 设置：
	 */
	public void setDbpassword(String dbpassword) {
		this.dbpassword = dbpassword;
	}
	/**
	 * 获取：
	 */
	public String getDbpassword() {
		return dbpassword;
	}
	/**
	 * 设置：
	 */
	public void setDetectingsql(String detectingsql) {
		this.detectingsql = detectingsql;
	}
	/**
	 * 获取：
	 */
	public String getDetectingsql() {
		return detectingsql;
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
