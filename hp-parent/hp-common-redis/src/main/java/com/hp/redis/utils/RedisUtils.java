package com.hp.redis.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

@Component
public class RedisUtils {
	private static final Logger LOG = LoggerFactory.getLogger(RedisUtils.class);
	
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;
	@Autowired
	private ValueOperations<String,String> valueOperations;
	@Autowired
	private HashOperations<String,String,Object> hashOperations;
	@Autowired
	private ListOperations<String,Object> listOperations;
	@Autowired
	private SetOperations<String,Object> setOperations;
	@Autowired
	private ZSetOperations<String,Object> zSetOperations;
	
	//默认过期时长，单位：S
	public final static long DEFAULT_EXPIRE = 10 * 60;
	//不设置过期时长
	public final static long NOT_EXPIRE = -1;
	
	private final static Gson gson = new Gson();
	
	//判断redis中是否存在过期的key
	public boolean exists(final String key) {
		return redisTemplate.getConnectionFactory().getConnection().exists(key.getBytes());
	}
	
	//设置key的有效时间
	public void expire(String key) {
		redisTemplate.expire(key, DEFAULT_EXPIRE, TimeUnit.SECONDS);
	}
	
	//设置key的有效时间
	public void expire(String key,long expire) {
		redisTemplate.expire(key, expire, TimeUnit.SECONDS);
	}
	
	public void set(String key,Object value,long expire) {
		valueOperations.set(key, toJson(value));
		if(expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
	}
	
	public void set(String key,Object value) {
		set(key,value,DEFAULT_EXPIRE);
	}
	
	//设置hash值
	public void hmSet(String key,String hashKey,Object value,long expire) {
		hashOperations.put(key, hashKey, toJson(value));
		if(expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
	}
	
	public void hmSet(String key,Object hashKey,Object value,long expire) {
		HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
		if(expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
	}
	
	//hash 获取数据
	public Object hmGet(String key,String hashKey) {
		return hashOperations.get(key, hashKey);
	}
	
	public Object hmGet(String key,Object hashKey) {
		HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}
	
	public <T> T get(String key,Class<T> clazz,long expire) {
		String value = valueOperations.get(key);
		if(expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
		return value == null ? null : fromJson(value,clazz);
	}
	public <T> T get(String key,Class<T> clazz) {
		return get(key,clazz,NOT_EXPIRE);
	}
	
	public String get(String key,long expire) {
		String value = valueOperations.get(key);
		if(expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
		return value;
	}
	
	public String get(String key) {
		return get(key,NOT_EXPIRE);
	}
	
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	//Object转字符串
	public String toJson(Object object) {
		if(object instanceof Integer || object instanceof Long || object instanceof Float
				|| object instanceof Double || object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return gson.toJson(object);
	}
	
	//字符串转class类型
	public <T> T fromJson(String json,Class<T> clazz){
		return gson.fromJson(json,clazz);
	}
	
	public <T> void setList(String key,String hashKey,List<T> list, long expire) {
		hashOperations.put(key, hashKey,Base64.encodeBase64String(ObjectTranscoder.serialize(list)));
		if(expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
	}
	
	public <T> List<T> getList(String key,String hashKey){
		HashOperations<String,Object,Object> hash = redisTemplate.opsForHash();
		String in = (String) hash.get(key, hashKey);
		if(StringUtils.isEmpty(in)) {
			return null;
		}
		List<T> list = (List<T>) ObjectTranscoder.deserialize(Base64.decodeBase64(in));
		return list;
	}
	
	//添加分布式锁
	public boolean setRedisLock(String key,String value,long timeout) {
		boolean dateFlag = this.exists(key);
		if(dateFlag) {
			LOG.info("锁{}已存在",key);
			return false;
		}
		boolean setNx = redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), value.getBytes());
		if(setNx) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		LOG.info("锁{}添加成功",key);
		return true;
	}
}
