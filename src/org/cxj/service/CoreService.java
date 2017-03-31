package org.cxj.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.cxj.message.resp.Article;
import org.cxj.message.resp.NewsMessage;
import org.cxj.message.resp.TextMessage;
import org.cxj.pojo.BaiduPlace;
import org.cxj.pojo.UserLocation;
import org.cxj.util.BaiduMapUtil;
import org.cxj.util.MessageUtil;
import org.cxj.util.MySQLUtil;


/*
 * 核心服务类
 * @author cxj
 * @date1 2016-03-30
 * @date2 2016-04-13  修改实现业务逻辑
 * */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml格式的消息数据
		String respXml = null;
		//默认返回文本消息内容
		String respContent=null;
		try {
			// 调用parseXml方法解析请求消息
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// 事件推送
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("您好，欢迎关注微信服务平台！");
					// 将消息对象转换成xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 暂不做处理
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCANCODEWAITMSG)) {
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					// 根据key值判断用户点击的按钮
					if (eventKey.equals("1")) {
						String scanResult=requestMap.get("ScanResult");
						String scanType=requestMap.get("ScanType");
						
						respContent="扫描类型："+scanType+"\n"+"扫描结果:"+scanResult;
						//respContent="received.1";
						
						textMessage.setContent(respContent);
						respXml = MessageUtil.messageToXml(textMessage);
					}
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					// 事件KEY值，与创建菜单时的key值对应
					String eventKey = requestMap.get("EventKey");
					// 根据key值判断用户点击的按钮
					if (eventKey.equals("21")) {
						
						respContent="get info";
						
						textMessage.setContent(respContent);
						respXml = MessageUtil.messageToXml(textMessage);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respXml;
	}
}
