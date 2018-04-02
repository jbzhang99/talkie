package com.meta.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class JacksonUtil
 *
 * json字符与对像转换
 * @version: $Revision$ $Date$ $LastChangedBy$
 *
 */
public final class JacksonUtil {
	//日志
	protected static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

	private static ObjectMapper objectMapper;

	/**
	 * 把JavaBean转换为json字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSon(Object object) {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}

		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return null;
	}
	
	/**
	 * 使用泛型方法，把json字符串转换为相应的JavaBean对象。
	 * (1)转换为普通JavaBean：readValue(json,Student.class)
	 * (2)转换为List,如List<Student>,将第二个参数传递为Student
	 * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List
	 * 
	 * @param jsonStr
	 * @param valueType
	 * @return
	 * @throws Exception 
	 */
	public static <T> T readValue(String jsonStr, Class<T> valueType) throws Exception {
		
		if(jsonStr==null){
			return null;
			}
		if(jsonStr==""){
			return null;
		}
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		try {
			T entity = objectMapper.readValue(jsonStr, valueType);
			return entity;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new Exception(e.getMessage());
		}

	}





	
}