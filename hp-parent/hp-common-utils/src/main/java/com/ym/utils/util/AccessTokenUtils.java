package com.ym.utils.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * @author 唐夏联
 * @copyright(c) 三力士智能装备上海分公司
 * @fileName AccessTokenUtils.java
 * @version 1.0.0
 * @date 2018年6月19日 
 * @description 
 * @others 
 * @functionList
 * @history
 */
@Component
public class AccessTokenUtils {

	private static final Logger LOG = LoggerFactory.getLogger(AccessTokenUtils.class);

	private final static String fileUrl = "/tmp/ftp/pub/sale/ACCESS_TOKEN.TXT";
	//private final static String fileUrl = "D:/ACCESS_TOKEN.TXT";

	//appId
	private final static String APPID = "wx33bb55f311221f2b";//公司
	//private final static String APPID = "wx01c96849afeb2594";
	//appscret 
	private final static String APPSECRET = "21b96a238fe6cb8c286779bed36ac071";//公司
	//private final static String APPSECRET = "f7276a1724fa590664aa5d5d4abcfaf9";

	File file = new File(fileUrl);

	//access_token_url
	private final static String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	//授权方式，应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
	//private final static String SCOPE="snsapi_base";
	//通过code来获取ACCESS_TOKEN的url  这里获取到的access_token和基础的access_token不一样，仅用户获取用户的openid
	private final static String USER_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	private final static String GET_USER_INFO_URL="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	// 静态初使化当前类
	public static AccessTokenUtils accessTokenUtils;
	@PostConstruct
	public void init() {
		accessTokenUtils = this;
	}

	/**
	 * Description: 发送get请求到微信，获取access_token
	 * @author    : 唐夏联
	 * @date      : 2018年10月23日
	 * @version   : 1.0
	 * Modified by 唐夏联 at 2018年10月23日
	 */
	public String getAccessToken() {
		LOG.info("-------------------------------开始获取微信accessToken-----------------------------");
		/**
		 * 获取accessToken
		 * @param appID		微信公众号凭证
		 * @param appScret	微信公众号凭证秘钥
		 * @return
		 */
		try {
			String url = AccessTokenUtils.ACCESS_TOKEN_URL;
			url=url.replaceAll("APPID", AccessTokenUtils.APPID);
			url=url.replaceAll("APPSECRET", AccessTokenUtils.APPSECRET);
			//往微信发送一个get请求获取access_token
			URL	getUrl = new URL(url);
			HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
			http.setRequestMethod("GET"); 
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();
			InputStream is = http.getInputStream(); 
			int size = is.available(); 
			byte[] b = new byte[size];
			is.read(b);
			String message = new String(b, "UTF-8");
			LOG.info("微信返回的信息为:{}",message);
			//获取到json之后取出返回json里的access_token
			JSONObject json = JSONObject.parseObject(message);
			//取到access_token
			String access_token = json.getString("access_token");
			return access_token;
		} catch (IOException e) {
			LOG.error("错误信息为:{}",e.getMessage());
		}
		return null;
	}

	/**
	 * Description: 从文件中读取access_token
	 * @author    : 唐夏联
	 * @date      : 2018年10月23日
	 * @version   : 1.0
	 * Modified by 唐夏联 at 2018年10月23日
	 */
	public String accessTokenByTxt() {
		LOG.info("-----开始读取文件中的accessToken-----");
		try {
			String encoding="GBK";
			if(file.isFile() && file.exists()){ //判断文件是否存在
				BufferedReader in = new BufferedReader(new FileReader(fileUrl));
				String str;
				String access_token=null;
				if ((str = in.readLine()) != null) {
					access_token = str;
				}
				LOG.info("读取到的accessToken为:{}",access_token);
				return access_token;	
			}
		} catch (IOException e) {
			LOG.error("错误信息为:{}",e.getMessage());
		}
		return null;
	}

	/**
	 * Description: 将access_token写入文件中
	 * @author    : 唐夏联
	 * @date      : 2018年10月23日
	 * @version   : 1.0
	 * Modified by 唐夏联 at 2018年10月23日
	 */
	public void writeAccessToken(String access_token) {
		LOG.info("-------------------------开始执行写入文件-------------------------");
		try {
			if (!file.exists()) {
				LOG.info("-------------------------开始创建文件-------------------------");
				file.createNewFile();
				LOG.info("-------------------------创建文件成功-------------------------");
			}
			FileOutputStream fos = new FileOutputStream(file, false);// 不允许追加
			LOG.info("-------------------------开始写入-------------------------");
			fos.write(access_token.getBytes());  
			fos.close();
			LOG.info("-------------------------写入完成-------------------------");
		} catch (IOException e) {
			LOG.error("错误信息为:{}",e.getMessage());
		}
	}

	/**
	 * Description: 通过code来换取accessToken
	 * @author    : 唐夏联
	 * @date      : 2018年10月23日
	 * @version   : 1.0
	 * Modified by 唐夏联 at 2018年10月23日
	 */
	public JSONObject getWeChatAccessTokenByCode(String code) throws IOException {
		String url = AccessTokenUtils.USER_ACCESS_TOKEN_URL;
		url = url.replaceAll("APPID", AccessTokenUtils.APPID).replace("SECRET",AccessTokenUtils.APPSECRET).replace("CODE", code);
		URL getUrl=new URL(url);
		HttpURLConnection http=(HttpURLConnection)getUrl.openConnection();
		http.setRequestMethod("GET"); 
		http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		http.setDoOutput(true);
		http.setDoInput(true);
		http.connect();
		InputStream is = http.getInputStream(); 
		int size = is.available(); 
		byte[] b = new byte[size];
		is.read(b);
		String message = new String(b, "UTF-8");
		//获取到json之后取出返回json里的access_token
		JSONObject json = JSONObject.parseObject(message);
		LOG.info("接收到的json参数为:{}",json.toJSONString());
		//获取json中的openID，scope_base的静默授权就完成了

		/**
		 * { "access_token":"ACCESS_TOKEN",
			"expires_in":7200,
			"refresh_token":"REFRESH_TOKEN",
			"openid":"OPENID",
			"scope":"SCOPE" }
		 * 正确时返回的参数，一个access_token有效期为7200秒，后续可以考虑是否使用refresh_token获取有效期为30天的token
		 * 
		 * {"errcode":40029,"errmsg":"invalid code"}
		 * 错误时返回的参数
		 * */
		return json;
	}


	/**
	 * Description: 拉取用户信息
	 * @author    : 唐夏联
	 * @date      : 2018年10月24日
	 * @version   : 1.0
	 * Modified by 唐夏联 at 2018年10月24日
	 * @throws IOException 
	 */
	public JSONObject getUserInfo(String accessToken,String openId) throws IOException {
		String url = AccessTokenUtils.GET_USER_INFO_URL;
		url = url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		LOG.info("GET请求的地址为:{}",url);
		URL getUrl = new URL(url);
		HttpURLConnection http = (HttpURLConnection) getUrl.openConnection();
		http.setRequestMethod("GET");
		http.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		http.setDoOutput(true);
		http.setDoInput(true);
		http.connect();
		InputStream is = http.getInputStream(); 
		int size = is.available(); 
		byte[] b = new byte[size];
		is.read(b);
		String message = new String(b, "UTF-8");
		LOG.info("接收到的参数为:{}",message);
		/**
		 * message格式:
		 * {
		 * "openid":"oJVzm1XHncRl6EbnELciHe_25WY8",
		 * "nickname":"'.'","sex":1,"language":"zh_CN",
		 * "city":"浦东新区",
		 * "province":"上海",
		 * "country":"中国",
		 * "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTJfia74qOtb5aaIBsXRQxfjFbJhR1fkqpJShRPT8h58VBK8CNicia50fcllCyfOk8jiaICdqJEiawGop3A\/132",
		 * "privilege":[]
		 * }
		 * */
		JSONObject json = JSONObject.parseObject(message);
		return json;
	}


}

