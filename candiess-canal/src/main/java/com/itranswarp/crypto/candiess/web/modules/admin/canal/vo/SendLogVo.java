package com.itranswarp.crypto.candiess.web.modules.admin.canal.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SendLogVo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * log文件名称
	 */
	private String logfileName;
	/**
	 * log文件的下标
	 */
	private Long logfileOffse;
	/**
	 * 数据库的名称
	 */
	private String dbName;
	/**
	 * 数据库表的名称
	 */
	private String tableName;
	/**
	 * key散布的值，同一个key会散布到同一个磁道上
	 */
	private String key;
	/**
	 * 更改的类型
	 */
	private ChangeEventType changeEventType;
	/**
	 * 更改的数据
	 */
	private List<Object> data;
	/**
	 * 更改前的数据
	 */
	private List<Object> olddata;

	public String getLogfileName() {
		return logfileName;
	}

	public void setLogfileName(String logfileName) {
		this.logfileName = logfileName;
	}

	public Long getLogfileOffse() {
		return logfileOffse;
	}

	public void setLogfileOffse(Long logfileOffse) {
		this.logfileOffse = logfileOffse;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ChangeEventType getChangeEventType() {
		return changeEventType;
	}

	public void setChangeEventType(ChangeEventType changeEventType) {
		this.changeEventType = changeEventType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public List<Object> getOlddata() {
		return olddata;
	}

	public void setOlddata(List<Object> olddata) {
		this.olddata = olddata;
	}
}
