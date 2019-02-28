package com.ym.utils.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 微信砍价工具
 * 
 * @author : 唐夏联
 * @date : 2018年11月19日 
 * Company : 上海煜墨信息科技有限公司
 * Copyright : Copyright (c) 2018
 * @version : 1.0 Modified by 唐夏联 at 2018年11月19日
 */
public class WXBargainUtils {
	/**
	 * Description: 生成微信砍价数组
	 * 
	 * @author : 唐夏联
	 * @date : 2018年11月19日 
	 * Company : 上海煜墨信息科技有限公司 
	 * Copyright : Copyright (c) 2018
	 * @version : 1.0 Modified by 唐夏联 at 2018年11月19日
	 */

	private static final Double TIMES = 2.1;

	// 获取砍价集合
	public static List<Double> splitRedPackets(Double money, int count, Double minMoney, Double maxMoney) {
		if (!isRight(money, count, minMoney, maxMoney)) {
			return null;
		}
		List<Double> list = new ArrayList<Double>();
		Double max = (Double) (money * TIMES / count);

		max = max > maxMoney ? maxMoney : max;
		for (int i = 0; i < count; i++) {
			Double one = randomRedPacket(money, minMoney, max, count - i, minMoney, maxMoney);
			list.add(one);
			money -= one;
		}
		return list;
	}

	private static boolean isRight(Double money, int count, Double minMoney, Double maxMoney) {
		double avg = money / count;
		if (avg < minMoney) {
			return false;
		} else if (avg > maxMoney) {
			return false;
		}
		return true;
	}

	private static Double randomRedPacket(Double money, Double mins, Double maxs, int count, Double minMoney,
			Double maxMoney) {
		if (count == 1) {
			return (double) (Math.round(money * 100)) / 100;
		}
		if (mins == maxs) {
			return mins;// 如果最大值和最小值一样，就返回mins
		}
		Double max = maxs > money ? money : maxs;
		Double one = ((Double) Math.random() * (max - mins) + mins);
		one = (double) (Math.round(one * 10)) / 10;
		Double moneyOther = money - one;
		if (isRight(moneyOther, count - 1, minMoney, maxMoney)) {
			return one;
		} else {
			// 重新分配
			Double avg = moneyOther / (count - 1);
			if (avg < minMoney) {
				return randomRedPacket(money, mins, one, count, minMoney, maxMoney);
			} else if (avg > maxMoney) {
				return randomRedPacket(money, one, maxs, count, minMoney, maxMoney);
			}
		}
		return one;
	}

}
