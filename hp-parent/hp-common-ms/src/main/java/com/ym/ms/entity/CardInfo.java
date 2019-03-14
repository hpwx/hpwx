package com.ym.ms.entity;

public class CardInfo {

	public String activating_time;
	public String custom_package_name;
	public String custom_pool_name;
	public String down_time;
	public String expire_time;
	public int flag;
	public String iccid;
	public String init_useing_time;
	public String max_usable;
	public String monthly_already_usage;
	public String monthly_can_usage;

	public String msisdn;

	public String period_number;

	public String start_useing_time;
	public String note;

	@Override
	public String toString() {
		return "CardInfo [activating_time=" + activating_time + ", custom_package_name=" + custom_package_name
				+ ", custom_pool_name=" + custom_pool_name + ", down_time=" + down_time + ", expire_time=" + expire_time
				+ ", flag=" + flag + ", iccid=" + iccid + ", init_useing_time=" + init_useing_time + ", max_usable="
				+ max_usable + ", monthly_already_usage=" + monthly_already_usage + ", monthly_can_usage="
				+ monthly_can_usage + ", msisdn=" + msisdn + ", period_number=" + period_number + ", start_useing_time="
				+ start_useing_time + ", note=" + note + "]";
	}
}
