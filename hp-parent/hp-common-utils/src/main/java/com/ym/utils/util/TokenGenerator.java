package com.ym.utils.util;

import java.security.MessageDigest;
import java.util.UUID;

public class TokenGenerator {
	private static final char[] hexCode="0123456789abcdef".toCharArray();
	public static String generateValue() {
		return generateValue(UUID.randomUUID().toString());
	}
	
	public static String generateValue(String param) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(param.getBytes());
			byte[] messageDigest = algorithm.digest();
			return toHexString(messageDigest);
		}catch(Exception e) {
			throw new RuntimeException("生成Token失败",e);
		}
	}
	
	private static String toHexString(byte[] data) {
		if(data == null) {
			return null;
		}
		StringBuilder r = new StringBuilder(data.length * 2);
		for(byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}
	
	
	public  static String  getnerateOrderId() {
		
		int r1=(int)(Math.random()*(10));//产生2个0-9的随机数
		int r2=(int)(Math.random()*(10));
		long now = System.currentTimeMillis();//一个13位的时间戳
		String orderno =String.valueOf(r1)+String.valueOf(r2)+String.valueOf(now);
		return orderno;
	}
}
