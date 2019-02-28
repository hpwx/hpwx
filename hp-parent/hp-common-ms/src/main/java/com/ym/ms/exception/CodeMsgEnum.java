package com.ym.ms.exception; 
/**
 * Description: 返回码和返回信息定义
 * @author    : 郁如义
 * @date      : 2018年9月9日
 * Company    : 上海煜墨信息科技有限公司
 * Copyright  : Copyright (c) 2018
 * @version   : 1.0
 * Modified by 郁如义 at 2018年9月9日
 */
public enum CodeMsgEnum {
	//成功标识
	SUCCESS("000000","成功"),
	ERROR("0000500","内部错误"),

	//举例: 00: 代表 api接口  01：代码用户工程  001： 用户工程的错误码依次递增
	ERROR_USER_PARAM("0001001","用户参数错误!"),
	//用户不存在
	ERROR_USER_NOT_EXIST("0001002","用户不存在!"),
	//用户第一次登陆
	ERROR_USER_FIRSTLOGIN("0001003","请填写信息！"),
	//手机更新失败
	ERROR_USER_PHONE_UPDATE_FAIL("0001004","手机维护失败！"),
	//手机以绑定过微信号
	ERROR_USER_PHONE_IS_USERED("0001005","手机已绑定过微信号！"),
	//该手机号已被注册过，请登录
	ERROR_USER_PHONE_YES_REGISTERED("0001006","该手机号已被注册过，请登录！"),
	//获取手机验证码失败！请重新获取
	ERROR_USER_PHONE_NO_FAILURE("0001007","获取手机验证码失败！请重新获取！"),
	//手机参数输入有误
	ERROR_PHONE_PARAM("0004015","输入号码的参数有误!"),
	//输入正确的手机号
	ERROR_PHONE_TRUE("0004016","请输入正确的手机号!"),
	//手机号有误
	ERROR_PHONE_FALSE("0004017","输入的手机号有误!"),
	//手机号尚未注册
	ERROR_PHONE_NO("0004018","该手机号尚未注册，请注册!"),
	//验证码失效重新获取
	ERROR_NUMBER_NO("0004019","验证码失效请重新获取!"),
	//验证码不正确，请重新输入
	ERROR_NUMBER_ERROR("0004020","验证码不正确，请重新输入!"),
	//还未获取验证码，请先获取
	ERROR_NUMBER_GET("00040201","还未获取验证码，请先获取!"),
	//验证手机验证码出现错误，请重试
	ERROR_NUMBER_VERIFICATION("00040202","验证手机验证码出现错误，请重试!"),
	//用户还未通过验证码验证
	USER_ERROR_NUMBER_VERIFICATION("00040203","用户还未通过验证码验证!"),
	//用户名和密码不匹配，登录失败!
	USER_ERROR_NUMBER_PASSWORD("00040204","用户名和密码不匹配，登录失败!"),
	//请先微信授权
	USER_ERROR_weiChat_AUTH("00040205","请先微信授权!"),
	//输入手机号有误
	USER_ERROR_PHONE_NUM("00040206","输入手机号有误"),
	//用户编号有误
	USER_ERROR_NUM("00040207","用户编号有误"),
	//收货人姓名不能为空
	USER_ERROR_NAME("00040208","收货人姓名不能为空"),
	//收货人联系方式不能为空
	USER_NULL_PHONE("00040209","收货人联系方式不能为空"),
	//省份不能为空
	USER_NULL_PROVINCE("00040210","省份不能为空"),
	//城市不能为空
	USER_NULL_CITY("00040211","城市不能为空"),
	//区县不能为空
	USER_NULL_AIRE("00040212","区县不能为空"),
	//详细地址不能为空
	USER_NULL_ADDRESS("00040213","详细地址不能为空"),
	//默认地址
	DEFAULT_ADDRESS("00040214","已经是默认地址,请勿重复设置"),
		
	
	
	
	//群组不存在
	ERROR_GROUP_NOT_EXIST("0002001","群组不存在!"),
	//群组接收参数错误
	ERROR_GROUP_PARAM("0002002","群组参数有误!"),
	//群组创建失败
	ERROR_GROUP_FAIL("0002003","群组创建失败！"),
	//没有解散群的权限
	ERROR_GROUP_NOMANAGEMENT("0002004","你没有解散该群的权限！"),
	//不需要再次加群
	ERROR_GROUP_ADDGROUP("0002005","该群为您自己创建，不需要再次加群！"),
	//先绑定水杯
	ERROR_GROUP_BINDINGDEVICE("0002006","您没有可加入群组的水杯，请先绑定水杯！"),
	//已经在群组中
	ERROR_GROUP_INGROUP("0002007","已有水杯在该群组中，请在群组内部添加水杯!"),
	//加入群组失败
	ERROR_GROUP_INGROUPFAIL("0002008","加入失败!"),
	//添加失败
	ERROR_GROUP_ADDNOTICEFAIL("0002009","添加失败!"),
	//未绑定设备
	ERROR_GROUP_NODEVICE("0002010","杯子还未被绑定！"),
	//二维码格式不正确
	ERROR_GROUP_2WMERROR("0002011","二维码格式不正确！"),
	//设备接收参数有误
	ERROR_DEVICE_PARAM("0003001","设备参数有误！"),
	//设备号无法识别
	ERROR_DEVICE_CODEERROR("0003002","设备编号无法识别!"),
	//二维码有误
	ERROR_DEVICE_2WMERROR("0003003","二维码不正确!"),
	//绑定失败
	ERROR_DEVICE_BINDFAIL("0003004","绑定失败！"),
	//解绑失败
	ERROR_DEVICE_UNBINDFAIL("0003005","解绑失败!"),
	//添加失败
	ERROR_DEVICE_ADDFAIL("0003006","添加失败!"),
	//删除失败
	ERROR_DEVICE_DELETEFAIL("0003007","删除失败！"),
	//管理员重复绑定
	ERROR_DEVICE_BINDADMIN("0003008","您已是该水杯的管理员，请勿重复绑定！"),
	//请勿重复绑定
	ERROR_DEVICE_BINDREPEAT("0003009","您已绑定过该设备，请勿重新绑定！"),
	//设备不存在
	ERROR_DEVICE_NOTFINDDEVICE("0003010","该设备不存在！"),
	//重复绑定请求
	ERROR_DEVICE_REQUESTREPEAT("0003011","绑定请求已发送，请勿重复请求！"),
	//未激活设备
	ERROR_DEVICE_ACTIVEDEVICE("0003012","请先开机激活设备!"),
	//未找到对应规则
	ERROR_DEVICE_NOROLE("0003013","未找到对应的标准!"),
	//非第一次绑定
	DRROR_DEVICE_ISNOTFIRST("0003014","非第一次绑定"),
	//最多只能添加三个水杯
	ERROR_ADD_GROUP("0003015","创建群组已满！最多只能创建3个"),
	
	//微信openId获取有误,code失效
	ERROR_WEIXIN_LOGIN("0004001","网页授权失败，请关闭网页后重试。"),
	//微信用户信息获取有误
	ERROR_WEIXIN_USERINFO("0004002","获取用户信息有误。"),
	//微信获取openId有误code为null
	ERROR_WEIXIN_CODE("0004004","登陆信息有误请重试。"),
	//微信登陆失效
	ERROR_WEIXIN_TIMEOUT("0004003","登陆失效，请重新进入网页。"),
	//未注册
	ERROR_WEIXIN_REGISTER("0004005","请先成为加盟商。"),
	//二维码信息有误
	ERROR_WEIXIN_WM2("0004006","二维码信息有误。"),
	//注册失败
	ERROR_WEIXIN_REGISTER_FAIL("0004007","用户信息获取有误。"),
	//用户启用失败
	ERROR_WEIXIN_ENABLE("0004008","注册失败,请联系管理员。"),
	//已注册
	ERROR_WEIXIN_IS_REGISTER("0004009","已注册，请勿重复扫描二维码。"),
	//未查询到上级
	ERROR_WEIXIN_PARENT("0004010","获取上级加盟商有误。"),
	//参数有误
	ERROR_WEIXIN_PARAM("0004011","接收参数有误。"),
	//等级计算错误
	ERROR_WEIXIN_LEVEL("0004012","加盟商等级错误。"),
	//上级加盟返利失败
	ERROR_WEIXIN_FIRST_JOIN_REBATE_FAIL("0004013","获取直属下级加盟返利错误。"),
	//上上级加盟返利失败
	ERROR_WEIXIN_SECOND_JOIN_REBATE_FAIL("0004014","获取下级加盟返利错误。"),
	//二维码生成失败
	ERROR_WEIXIN_WM2_FAIL("0004015","二维码生成失败"),
	//返利记录插入失败
	ERROR_WEIXIN_JOIN_REBATE_RECORD("0004016","获取返利记录错误。"),
	//3级以内不涉及返利
	ERROR_WEIXIN_NO_REBATE("0004017","不涉及返利。"),
	//超出A级上线
	ERROR_WEIXIN_LEVEL1NUM("0004018","超出A级上限。"),
	//非常规流程，先生成二维码
	ERROR_WEIXIN_WM2_REGISTER("0004019","请点击生成二维码按钮生成我的二维码。"),
	//用户信息保存失败
	ERROR_WEIXIN_UPDATE_USERINFO("0004020","信息保存失败，请重试！"),
	
	//参数有误
	ERROR_ACT_PARAM("0005001","参数有误！"),
	//活动不存在
	ERROR_ACT_UNKNOWN("0005002","活动已过期。"),
	//未查询到用户信息，先注册
	ERROR_ACT_NO_USER("0005003","请填写用户信息！"),
	//用户信息不完整
	ERROR_ACT_USERINFO("0005004","请填写用户信息！"),
	//手机格式不正确
	ERROR_ACT_PHONE("0005005","手机号格式不正确！"),
	//用户填写信息有误
	ERROR_ACT_ERROR_USERINFO("0005006","请填写正确信息！"),
	//活动未开始
	ERROR_ACT_BEGIN_TIME("0005007","活动未开始,请耐心等待！"),
	//活动已结束
	ERROR_ACT_END_TIME("0005008","活动已结束!"),
	//已有参加中的活动
	ERROR_ACT_IS_JOIN("0005009","已有参与中的活动,请勿重复报名活动！"),
	//报名失败
	ERROR_ACT_REGISTER("0005010","报名失败，请重试。"),
	//重复砍价
	ERROR_ACT_BARGAIN("0005011","您已经砍过了,再点也没有用...."),
	//已是最低价
	ERROR_ACT_MIN_PRICE("0005012","砍价已完成，去购买吧..."),
	
	//  积分不够 
	ERROR_SCORENOTENOUGH("000080","积分不够，无法兑换礼品"),
	
	//  你已兑换过该类礼品，请先支付
	ERROR_PleasePayAlreadyExchange("000081","你已兑换过该类礼品，请先支付"),
	
	
	//  你已兑换该礼品 
	ERROR_AlreadyExchange("000082","你已兑换过该类礼品"),
	
//  未查到地址信息
	ERROR_NOTFINDADDRESS("000083","请选择地址！"),
	
	ERROR_NOproduct("000084","商品編不能为空！"),
	ERROR_buycount("000085","选择购买数量！");
	
	
	private String code;
	private String msg;

	private CodeMsgEnum(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
