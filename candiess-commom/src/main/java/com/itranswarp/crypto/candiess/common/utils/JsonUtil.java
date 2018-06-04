package com.itranswarp.crypto.candiess.common.utils;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;

public class JsonUtil {

	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * 转换为普通JavaBean：readValue(json,Student.class)
	 * 转换为List:readValue(json,List.class ).但是如果我们想把json转换为特定类型的List，比如List
	 * <Student>，就不能直接进行转换了。 因为readValue(json,List .class)返回的其实是List
	 * <Map>类型，你不能指定readValue()的第二个参数是List< Student>.class，所以不能直接转换。
	 * 我们可以把readValue()的第二个参数传递为Student[].class.然后使用Arrays
	 * .asList();方法把得到的数组转换为特定类型的List。 转换为Map：readValue(json,Map.class)
	 * 我们使用泛型，得到的也是泛型
	 * 
	 * @param content
	 *            要转换的JavaBean类型
	 * @param valueType
	 *            原始json字符串数据
	 * @return JavaBean对象
	 */
	public static <T> T toObject(String content, Class<T> valueType) {
		if (StringUtils.isEmpty(content)) {
			return null;
		}
		try {
			return JSON.parseObject(content, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把JavaBean转换为json字符串 普通对象转换：toJson(Student) List转换：toJson(List)
	 * Map转换:toJson(Map) 我们发现不管什么类型，都可以直接传入这个方法
	 * 
	 * @param object
	 *            JavaBean对象
	 * @return json字符串
	 */
	public static String toJson(Object object) {
		if (object == null) {
			return null;
		}
		try {
			ValueFilter filter = new ValueFilter() {
				@Override
				public Object process(Object object, String name, Object value) {
					if (value instanceof BigDecimal) {
						return ((BigDecimal) value).stripTrailingZeros().toPlainString();
					}
					return value;
				}
			};
			return JSONObject.toJSONString(object, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
