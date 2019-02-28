package com.ym.utils.util;

import java.util.Arrays;

public class HexUtils {
	
	public static String stringToAscii(String value)  
	{  
	    StringBuffer sbu = new StringBuffer();  
	    char[] chars = value.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	        if(i != chars.length - 1)  
	        {  
	            sbu.append((int)chars[i]).append(",");  
	        }  
	        else {  
	            sbu.append((int)chars[i]);  
	        }  
	    }  
	    return sbu.toString();  
	}  
	

	/**
	 *  字节数组转换为十六进制的字符串
	 * 
	 * */
	public static String BinaryToHexString(byte[] bytes){

		String hexStr =  "0123456789ABCDEF";
		String result = "";  
		String hex = "";  
		for(int i=0;i<bytes.length;i++){  
			
			//字节高4位  
			hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));  
			//字节低4位  
			hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));  
			result +=hex+" ";  //这里可以去掉空格，或者添加0x标识符。
		}  
		return result;  
	}
	
	/** 
     * 字符串转换成十六进制字符串
     */  
    public static String str2HexStr(String str) {  
        char[] chars = "0123456789ABCDEF".toCharArray();  
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();  
        int bit;  
        for (int i = 0; i < bs.length; i++) {  
            bit = (bs[i] & 0x0f0) >> 4;  
            sb.append(chars[bit]);  
            bit = bs[i] & 0x0f;  
            sb.append(chars[bit]);  
        }  
        return sb.toString();  
    } 
    
    //int转成2位字节数组
    
    public static byte[] unsignedShortToByte2(int s) {  
        byte[] targets = new byte[2];  
        targets[0] = (byte) (s >> 8 & 0xFF);  
        targets[1] = (byte) (s & 0xFF);  
        return targets;  
    }  

	/**
	 *  十六进制字符串转为字节数组 要求，不要存在0x这样的标识符
	 * 例如0x120x21必须先除掉0x再调用方法
	 * 
	 * */

	public static byte[] HexStrToBytes(String str)
	{
		//如果字符串长度不为偶数，则追加0
		/*if(str.length() % 2 !=0){
			str = "0"+str;
		}*/

		byte[] b = new byte[str.length() / 2];
		byte c1, c2;
		for (int y = 0, x = 0; x < str.length(); ++y, ++x)
		{
			c1 = (byte)str.charAt(x);
			if (c1 > 0x60) c1 -= 0x57;
			else if (c1 > 0x40) c1 -= 0x37;
			else c1 -= 0x30;
			c2 = (byte)str.charAt(++x);
			if (c2 > 0x60) c2 -= 0x57;
			else if (c2 > 0x40) c2 -= 0x37;
			else c2 -= 0x30;
			b[y] = (byte)((c1 << 4) + c2);
		}
		return b;
	}

	private static int hexChar2Decimal(char charAt) {
		if (charAt >= 'A' && charAt <= 'F')
			return charAt - 'A' + 10;// A~F转换成10进制数
		else if (charAt >= '0' && charAt <= '9')
			return charAt - '0';// 0~9字符转换成10进制
		else
			return -1;
	}

	/***
	 * 16进制数据转化为10进制
	 * @param hex
	 * @return
	 */
	public static int hex2Decimal(String hex) {
		int decimal = 0;
		for (int i = 0; i < hex.length(); i++) {
			if (hexChar2Decimal(hex.charAt(hex.length() - 1 - i)) != -1) {// 从16进制数的最后一个字符开始获取
				decimal = (int) (decimal + hexChar2Decimal(hex.charAt(hex.length() - 1 - i)) * Math.pow(16, i));// 乘以16的0次幂，然后++
			} else {
				// System.out.println("enter error, decimal will be zero!");//如果等于-1则是非法字符
				break;
			}
		}
		return decimal;
	}
	
	public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
	

}
