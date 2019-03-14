package com.ym.utils.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 唐夏联
 * @copyright(c) 三力士智能装备上海分公司
 * @fileName Sha1Util.java
 * @version 1.0.0
 * @date 2018年6月15日 
 * @description 
 * @others 
 * @functionList
 * @history
 */
public class SecurtiyKitUtil {

	/**
	 *@function sha1
	 *@description  使用sha1模式加密
	 *@calls 
	 *@calledBy
	 *@tableAccessed
	 *@tableUpdated
	 *@input 
	 *@output
	 *@return 
	 *@others
	 */
	public static String sha1(String str)  {
		
		try {
			
			StringBuffer sb = new StringBuffer();
			
			MessageDigest instance = MessageDigest.getInstance("sha1");
			
			instance.update(str.getBytes());
			
			byte[] msg = instance.digest();
			
			for (byte b : msg) {
				
				sb.append(String.format("%02x",b));
				
			}
			
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	//sha1生成测试
	/*public static void main(String[] args) {
		
		String sha1 = SecurtiyKit.sha1("hello");
		
		System.out.println(sha1+"=sha1");
		
	}*/
	
}

