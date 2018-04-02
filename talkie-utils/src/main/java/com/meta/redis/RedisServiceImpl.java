package com.meta.redis;

import com.meta.json.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存服务实现类
 *
 */
@Repository
public class RedisServiceImpl implements RedisService{
	private static final Logger LOG = LoggerFactory.getLogger(RedisServiceImpl.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void putMapValue(String keys,String hashKey,String value){
		redisTemplate.opsForHash().put(keys, hashKey, value);
	}

	@Override
	public String getMapValue(String keys,String hashKey){
		return (String)redisTemplate.opsForHash().get(keys, hashKey);
	}

	@Override
	public void putMapObject(String keys,String hashKey,Object value){
		redisTemplate.opsForHash().put(keys, hashKey, JacksonUtil.toJSon(value));
	}

	@Override
	public  <T> T getMapObject(String keys,String hashKey,Class<T> valueType){
		String value = (String)redisTemplate.opsForHash().get(keys, hashKey);
		try {
			return (T)JacksonUtil.readValue(value, valueType);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setObject(String hashkey,Object value,Integer expireHouse){
	    redisTemplate.delete(hashkey);
	    redisTemplate.opsForValue().set(hashkey, JacksonUtil.toJSon(value));
	    redisTemplate.expire(hashkey, expireHouse, TimeUnit.HOURS);
	}

	@Override
	public void setObject(String hashkey,Object value,Integer expireHouse,TimeUnit expireType){
		redisTemplate.delete(hashkey);
	    redisTemplate.opsForValue().set(hashkey, JacksonUtil.toJSon(value));
	    redisTemplate.expire(hashkey, expireHouse,expireType);
	}

	/**
	 *
	 * @param hashkey 	key值
	 * @param value		需要保存的值
	 */
	@Override
	public void setObject(String hashkey,Object value){
		redisTemplate.delete(hashkey);
		redisTemplate.opsForValue().set(hashkey, JacksonUtil.toJSon(value));
	}

	/**
	 * 根据key值获取value
	 *
	 * @param hashKey
	 * @return
	 */
	@Override
	public String getObject(String hashKey){
		return redisTemplate.opsForValue().get(hashKey);
	}

	@Override
	public <T> T getObject(String hashKey,Class<T> valueType){
		String value = redisTemplate.opsForValue().get(hashKey);
		try {
			return (T)JacksonUtil.readValue(value, valueType);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteObject(String hashkey) {
		redisTemplate.delete(hashkey);
	}
}
