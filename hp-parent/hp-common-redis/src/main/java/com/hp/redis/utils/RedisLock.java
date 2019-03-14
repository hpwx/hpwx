package com.hp.redis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
	private static final Logger LOG = LoggerFactory.getLogger(RedisLock.class);
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// 是否锁定标识
	private volatile boolean locked = false;

	/**
	* Description: 设置redis锁
	* @author    : 郁如义
	* @date      : 2018年9月16日
	* @version   : 1.0
	* Modified by 郁如义 at 2018年9月16日
	* lockKey的命名规则：  业务名称_功能，  如： exchange_clearZero
	* expireMsecs单位： 毫秒
	 */
	public synchronized boolean lock(String lockKey, long expireMsecs) {
		long expires = System.currentTimeMillis() + expireMsecs + 1;
		String expiresStr = String.valueOf(expires);
		if (this.setNx(lockKey, expiresStr)) {
			locked = true;
			LOG.info("添加锁：{}",lockKey);
			return true;
		}
		String currentValue = this.get(lockKey); //redis中，lockKey对应的是超时时间
		if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
			// 设置锁并返回值
			String oldValue = this.getSet(lockKey, expiresStr);
			if (oldValue != null && oldValue.equals(currentValue)) {
				locked = true;
				LOG.info("锁超时，添加锁：{}",lockKey);
				return true;
			}
		}
		return false;
	}

	/**
	* Description: 释放锁
	* @author    : 郁如义
	* @date      : 2018年9月16日
	* @version   : 1.0
	* Modified by 郁如义 at 2018年9月16日
	 */
	public synchronized void unLock(String lockKey) {
		if (locked) {
			LOG.info("释放锁：{}", lockKey);
			redisTemplate.delete(lockKey);
			locked = false;
		}
	}

	// 获取锁
	private String get(final String key) {
		Object obj = redisTemplate.opsForValue().get(key);
		return obj != null ? obj.toString() : null;
	}

	// 设置互斥锁
	private boolean setNx(final String key, final String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	// 设置锁并返回
	private String getSet(final String key, final String value) {
		Object obj = redisTemplate.opsForValue().getAndSet(key, value);
		return obj != null ? obj.toString() : null;
	}

}
