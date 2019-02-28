package com.ym.utils.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

/**
 * @author 唐夏联
 * @copyright(c) 三力士智能装备上海分公司
 * @fileName WeixinMenu.java
 * @version 1.0.0
 * @date 2018年8月13日 
 * @description 
 * @others 
 * @functionList
 * @history
 */
public class WeixinMessageUtil {

	public static String handlerReceiveMsg(HttpServletRequest req) {
		Map<String, String> reqMsgToMap = reqMsgToMap(req);
		if(reqMsgToMap!=null) {
			String msgType = reqMsgToMap.get("MsgType");
				return handlerCommonMsg(reqMsgToMap);
		}
		return null;
	}
	
	private static String handlerCommonMsg(Map<String,String> maps) {//返回普通消息
		
		
		return null;
	}
	
	private static Map<String,String> reqMsgToMap(HttpServletRequest req){
		String xml = reqToXml(req); //xml转换String
		Map<String,String> maps = new HashMap<String,String>();
		Document document;
		try {
			document = DocumentHelper.parseText(xml); //dom4j转换到map
			Element root = document.getRootElement();
			List<Element> eles = root.elements();
			for (Element element : eles) {
				maps.put(element.getName(),element.getTextTrim());
			}
			return maps;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String reqToXml(HttpServletRequest req) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(req.getInputStream())); //转换流
			String str = null;
			StringBuffer sb = new StringBuffer();
			while((str=br.readLine())!=null) { //循环拼接到StringBuffer
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String mapToXml(Map<String,Object> maps) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		Set<String> keys = maps.keySet();
		for (String key : keys) {
			Object o = maps.get(key);
			if(o instanceof String) { //目前只用到String
				root.addElement(key).addText((String)o);
			}else {
				
			}
		}
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw);
		xw.setEscapeText(false);
		try {
			xw.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sw.toString();
	}
	
	public static Map<String,Object> createTextMsg(Map<String,String> msgMap,String content){
		Map<String,Object> tm = new HashMap<String,Object>();
		tm.put("ToUserName", msgMap.get("FromUserName"));
		tm.put("FromUserName", msgMap.get("ToUserName"));
		tm.put("CreateTime", System.currentTimeMillis()+"");
		tm.put("MsgType", "text");
		tm.put("Content", content);
		return tm;
	}
	
	
}

