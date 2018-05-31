package com.itranswarp.crypto.candiess.api.query;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.itranswarp.crypto.candiess.common.xss.SQLFilter;

/**
 * QueryViewVo data
 *
 * @author biqingguo
 */
public class QueryViewVo<T> {
	private Boolean _search;
	private Integer rows;
	private Integer page = 1;
	private String sidx;
	private String sord;
	private String order;
	private Integer limit = 10;
	private String filters;
	private String password;
	private JqgridQueryFilterVo jqgridQueryFilterVo = new JqgridQueryFilterVo();

	public Boolean get_search() {
		return this._search;
	}

	public void set_search(Boolean _search) {
		this._search = _search;
	}

	public Integer getRows() {
		return this.rows;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSidx() {
		return this.sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return this.sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getFilters() {
		return this.filters;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFilters(String filters) throws IOException {
		if (StringUtils.isNotEmpty(filters)) {
			this.jqgridQueryFilterVo = JSON.parseObject(filters, JqgridQueryFilterVo.class);
		}
		this.filters = filters;
	}

	public JqgridQueryFilterVo getJqgridQueryFilterVo() {
		return this.jqgridQueryFilterVo;
	}

	public void setJqgridQueryFilterVo(JqgridQueryFilterVo jqgridQueryFilterVo) {
		this.jqgridQueryFilterVo = jqgridQueryFilterVo;
	}

	public Page<T> getPageUtil() {
		Page<T> pageVo = new Page<T>(page, limit);
		String sidx = SQLFilter.sqlInject(getSidx());
		String order = SQLFilter.sqlInject(getOrder());
		// 排序
		if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)) {
			pageVo.setOrderByField(sidx);
			pageVo.setAsc("ASC".equalsIgnoreCase(order));
		}
		return pageVo;
	}

	public Wrapper<T> getWrapper() {
		Wrapper<T> wrapper = new EntityWrapper<T>();
		List<QueryFilter> rules = jqgridQueryFilterVo.getRules();
		String groupOp = jqgridQueryFilterVo.getGroupOp();
		for (QueryFilter queryFilter : rules) {
			switch (groupOp) {
			case "AND":
				wrapper.and();
				break;
			case "OR":
				wrapper.or();
				break;
			default:
				break;
			}
			queryFilter.setCriteria(wrapper);
		}
		return wrapper;
	}
}
