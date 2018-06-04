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
@TableName("canalpositions_config")
public class CanalpositionsConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Long dbconfigid;
	/**
	 * 
	 */
	private String journalname;
	/**
	 * 
	 */
	private Long position;
	/**
	 * 
	 */
	private Long timestamp;
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
	public void setJournalname(String journalname) {
		this.journalname = journalname;
	}
	/**
	 * 获取：
	 */
	public String getJournalname() {
		return journalname;
	}
	/**
	 * 设置：
	 */
	public void setPosition(Long position) {
		this.position = position;
	}
	/**
	 * 获取：
	 */
	public Long getPosition() {
		return position;
	}
	/**
	 * 设置：
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * 获取：
	 */
	public Long getTimestamp() {
		return timestamp;
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
