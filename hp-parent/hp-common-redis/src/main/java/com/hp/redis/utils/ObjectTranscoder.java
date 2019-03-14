package com.hp.redis.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectTranscoder {
	public static Logger logger = LoggerFactory.getLogger(ObjectTranscoder.class);
	//序列化
	public static byte[] serialize(Object value) {
		if(value == null) {
			throw new NullPointerException("value为null,不能序列化");
		}
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(value);
			os.close();
			bos.close();
			result = bos.toByteArray();
		}catch(IOException e) {
			throw new IllegalArgumentException("非序列化对象,请实现序列化接口",e);
		}finally {
			try {
				if(os != null) {
					os.close();
				}
				if(bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				logger.info(e.getMessage(),e);
			}
		}
		return result;
	}
	
	//反序列化
	public static Object deserialize(byte[] in) {
		Object result = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if(in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				result = is.readObject();
				is.close();
				bis.close();
			}
		}catch(Exception e) {
			logger.info(e.getMessage(),e);
		}finally {
			try {
				if(is != null) {
					is.close();
				}
				if(bis != null) {
					bis.close();
				}
			}catch(Exception e) {
				logger.info(e.getMessage(),e);
			}
		}
		return result;
	}
}
