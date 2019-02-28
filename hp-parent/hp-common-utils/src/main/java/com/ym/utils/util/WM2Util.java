package com.ym.utils.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WM2Util {
	
	/**
	 * 公司编码  companyCode  2
	 * 省份编号  provinceCode  2
	 * 颜色  colorCode  2
	 * 容量  volumeCode  1
	 * 产品系列 cupSerise  2
	 * 生产日期   madeDate  8
	 * 硬件二级模块唯一码   moduleCode  30
	 * */
	
	public static Map<String, Object> resolve2WM(String wm2Code){
		
		Map<String, Object> wm2Info = new HashMap<String, Object>();
		
		String companyCode = wm2Code.substring(0, 2);
		
		wm2Info.put("companyCode", companyCode);
		
		String provinceCode = wm2Code.substring(2, 4);
		
		Integer provinceCode1 = null;
		
		if(provinceCode!=null) {
			
			try {
				provinceCode1 = Integer.valueOf(provinceCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		wm2Info.put("provinceCode", provinceCode1);

		String colorCode = wm2Code.substring(4, 6);
		
		wm2Info.put("colorCode", colorCode);

		String volumeCode = wm2Code.substring(6, 7);
		
		Integer volumeCode1 = null;
		
		if(volumeCode!=null) {
			
			try {
				volumeCode1 = Integer.valueOf(volumeCode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		wm2Info.put("volumeCode", volumeCode1);

		String cupSerise = wm2Code.substring(7, 9);
		
		wm2Info.put("cupSerise", cupSerise);

		String madeStr = wm2Code.substring(9, 17);
		
		if(madeStr!=null) {
			
			try {
				Date madeDate = new SimpleDateFormat("yyyyMMdd").parse(madeStr);
				
				wm2Info.put("madeDate", madeDate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		String moduleCode = wm2Code.substring(17, 47);
		
		wm2Info.put("moduleCode", moduleCode);
		
		return wm2Info;
	}

}
