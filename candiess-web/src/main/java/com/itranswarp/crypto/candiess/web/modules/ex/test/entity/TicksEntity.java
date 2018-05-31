package com.itranswarp.crypto.candiess.web.modules.ex.test.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-21 15:07:11
 */
@TableName("ticks")
public class TicksEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String symbol;
	/**
	 * 
	 */
	private Long takerorderid;
	/**
	 * 
	 */
	private Long makerorderid;
	/**
	 * 
	 */
	private BigDecimal price;
	/**
	 * 
	 */
	private BigDecimal amount;
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
	 * 设置：
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	/**
	 * 获取：
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * 设置：
	 */
	public void setTakerorderid(Long takerorderid) {
		this.takerorderid = takerorderid;
	}
	/**
	 * 获取：
	 */
	public Long getTakerorderid() {
		return takerorderid;
	}
	/**
	 * 设置：
	 */
	public void setMakerorderid(Long makerorderid) {
		this.makerorderid = makerorderid;
	}
	/**
	 * 获取：
	 */
	public Long getMakerorderid() {
		return makerorderid;
	}
	/**
	 * 设置：
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：
	 */
	public BigDecimal getAmount() {
		return amount;
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
}
