package com.ym.utils.util;


public class LocationUtils {

	private static final Double EARTH_RADIUS = 6378.137;  

	
	//计算经纬度之间的距离
    private double rad(double d) {    
        return d * Math.PI / 180.0;    
    }
	    
    /**   
     * 通过经纬度获取距离(单位：米)   
     * @param 围栏中心点纬度
     * @param 围栏中心点经度
     * @param 设备当前纬度
     * @param 设备当前经度 
     * @return   
     */    
    public double getDistance(double latiude1, double longtiude1, double latiude2,    
                                     double longtiude2) {    
        double radLat1 = rad(latiude1);
        double radLat2 = rad(latiude2);
        double a = radLat1 - radLat2;
        double b = rad(longtiude1) - rad(longtiude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;    
        return Math.abs(s);
    }    
}


