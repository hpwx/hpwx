package com.ym.ms.entity;

import java.util.List;


   // {"code":0,"data":{"cards":[{"note":"","down_time":1561478400000,"flag":9,"monthly_already_usage":0.0,"max_usable":0.0,"period_number":1,"start_useing_time":1530028800000,"iccid":"89860617040055001491","custom_package_name":"联通20M/年","monthly_can_usage":20.0,"msisdn":861064647761768,"activating_time":1532056437535,"init_useing_time":1530028800000},{"note":"","down_time":1566748800000,"flag":9,"monthly_already_usage":0.0,"max_usable":0.0,"period_number":1,"start_useing_time":1535299200000,"iccid":"89860617040055001483","custom_package_name":"联通20M/年","monthly_can_usage":20.0,"msisdn":861064647761767,"activating_time":1535299422940,"init_useing_time":1535299200000}]},"message":"成功"}


   
//  获取 无量网卡 信息 
public class IOTCardInfo {

	public int code;
	public String message;
	public List<CardInfo> cards;
	 
}
