package com.itranswarp.crypto.candiess.api.query;

import java.util.Collection;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;

import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 查询过滤条件
 *
 * @author heguojun
 */
public class QueryFilter {

	/**
	 * 等于
	 */
	public static final String OPERATOR_EQ = "eq";
	/**
	 * 不等于
	 */
	public static final String OPERATOR_NE = "ne";
	/**
	 * 小于
	 */
	public static final String OPERATOR_LT = "lt";

	/**
	 * 小于等于
	 */
	public static final String OPERATOR_LE = "le";

	/**
	 * 大于
	 */
	public static final String OPERATOR_GT = "gt";

	/**
	 * 大于等于
	 */
	public static final String OPERATOR_GE = "ge";

	/**
	 * 开头是
	 */
	public static final String OPERATOR_BW = "bw";
	/**
	 * 开头不是
	 */
	public static final String OPERATOR_BN = "bn";

	/**
	 * 属于
	 */
	public static final String OPERATOR_IN = "in";
	/**
	 * 不属于
	 */
	public static final String OPERATOR_NI = "ni";
	/**
	 * 结尾是
	 */
	public static final String OPERATOR_EW = "ew";
	/**
	 * 结尾不是
	 */
	public static final String OPERATOR_EN = "en";
	/**
	 * 包含
	 */
	public static final String OPERATOR_CN = "cn";
	/**
	 * 不包含
	 */
	public static final String OPERATOR_NC = "nc";

	/**
	 * 为空
	 */
	public static final String OPERATOR_NULL = "nu";

	/**
	 * 不为空
	 */
	public static final String OPERATOR_NONNULL = "nn";
	/**
	 * 区间
	 */
	public static final String OPERATOR_BT = "bt";

	public static final String OPERATOR_UNLIKE = "unlike";

	private String field;

	private String op;

	private Object data;

	@SuppressWarnings("rawtypes")
	public void setCriteria(Wrapper wrapper) {
		if (this.data == null && !Objects.equals(this.op, QueryFilter.OPERATOR_NULL)
				&& !Objects.equals(this.op, QueryFilter.OPERATOR_NONNULL)) {
			return;
		}
		switch (this.op) {
		case QueryFilter.OPERATOR_EQ:
			wrapper.eq(this.field, this.data);
			// criteria.andEqualTo(this.field, this.data);
			break;
		case QueryFilter.OPERATOR_NE:
			wrapper.ne(this.field, this.data.toString());
			// criteria.andNotEqualTo(this.field, this.data.toString());
			break;
		case QueryFilter.OPERATOR_LT:
			wrapper.lt(this.field, this.data);
			// criteria.andLessThan(this.field, this.data);
			break;
		case QueryFilter.OPERATOR_LE:
			wrapper.le(this.field, this.data);
			// criteria.andLessThanOrEqualTo(this.field, this.data);
			break;
		case QueryFilter.OPERATOR_GT:
			// criteria.andGreaterThan(this.field, this.data);
			break;
		case QueryFilter.OPERATOR_GE:
			wrapper.ge(this.field, this.data);
			// criteria.andGreaterThanOrEqualTo(this.field, this.data);
			break;
		case QueryFilter.OPERATOR_IN:
			if (this.data instanceof Iterable) {
				wrapper.in(this.field, (Collection) this.data);
			}
			// wrapper.in(this.field, this.data);
			// if (this.data instanceof Iterable) {
			// criteria.andIn(this.field, (Iterable) this.data);
			// }
			break;
		case QueryFilter.OPERATOR_NULL:
			wrapper.isNull(this.field);
			// criteria.andIsNull(this.field);
			break;
		case QueryFilter.OPERATOR_NONNULL:
			wrapper.isNotNull(this.field);
			// criteria.andIsNotNull(this.field);
			break;
		case QueryFilter.OPERATOR_CN:
			wrapper.like(this.field, "%" + this.data.toString() + "%");
			// criteria.andLike(this.field, "%" + this.data.toString() + "%");
			break;
		case QueryFilter.OPERATOR_UNLIKE:
			wrapper.notLike(this.field, "%" + this.data.toString() + "%");
			// criteria.andNotLike(this.field, this.data.toString());
			break;
		default:
			break;
		}
	}

	/**
	 * @param example
	 *            装载条件的实体
	 * @param orderColumn
	 *            排序的列
	 * @param sort
	 *            true -> asc | false -> desc
	 * @param page
	 *            第几页
	 * @param limit
	 *            条数
	 */
	// public static void setOrderAndLimit(Example example, String orderColumn,
	// boolean sort, int page, int limit) {
	// String orderClause = orderColumn == null ? "id asc"
	// : QueryFilter.camel2underscore(orderColumn) + " " + (sort ? "asc" :
	// "desc");
	//
	// example.setOrderByClause(orderClause + " limit " + (page - 1) * limit +
	// "," + limit);
	// }

	public static String camel2underscore(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		}

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				result.append('_').append(Character.toLowerCase(c));
			} else {
				result.append(c);
			}
		}

		return result.toString();
	}

	public QueryFilter() {
	}

	public QueryFilter(String field, String op, Object data) {
		this.field = field;
		this.op = op;
		this.data = data;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getField() {
		return this.field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return this.op;
	}

	public void setOp(String op) {
		this.op = op;
	}

}
