package com.meta.redis;

import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存服务接口类
 *
 */
public interface RedisService {
	/**
	 * 将Value保存到redis缓存中
	 * @param keys
	 * @param hashKey
	 * @param value
	 */
	public void putMapValue(String keys, String hashKey, String value);

	/**
	 * 根据haskKey取redis缓存中的Value
	 * @param keys
	 * @param hashKey
	 * @return
	 */
	public String getMapValue(String keys, String hashKey);

	/**
	 * 将value对象保存到redis中，转JSON格式存储
	 * @param keys
	 * @param hashKey
	 * @param value
	 */
	public void putMapObject(String keys, String hashKey, Object value);

	/**
	 * 根据haskKey取redis缓存中的Value对象
	 * @param keys
	 * @param hashKey
	 * @return
	 */
	public  <T> T getMapObject(String keys, String hashKey, Class<T> valueType);

	/**
	 * 根据hashkey保存value对象(带设置失效时间)
	 * @param hashkey
	 * @param value
	 * @param expireHouse
	 */
	public void setObject(String hashkey, Object value, Integer expireHouse);


	/**
	 * 根据hashkey保存value对象(带设置失效时间,时间类型)
	 * @param hashkey
	 * @param value
	 * @param expireHouse
	 */
	public void setObject(String hashkey, Object value, Integer expireHouse, TimeUnit expireType);

	/**
	 *
	 * @param hashkey 	key值
	 * @param value		需要保存的值
	 */
	public void setObject(String hashkey, Object value);

	/**
	 * 根据key值获取value
	 *
	 * @param hashKey
	 * @return
	 */
	public String getObject(String hashKey);

	/**
	 * 根据hashkey 删除redis缓存中的 对象
	 * @param hashkey
	 */
	public void deleteObject(String hashkey);

	/**
	 * 根据 hashKey 获取valueType对象
	 * @param hashKey
	 * @param valueType
	 * @return
	 */
	public <T> T getObject(String hashKey, Class<T> valueType);
}
