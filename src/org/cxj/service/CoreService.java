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
 * ���ķ�����
 * @author cxj
 * @date1 2016-03-30
 * @date2 2016-04-13  �޸�ʵ��ҵ���߼�
 * */
public class CoreService {
	/**
	 * ����΢�ŷ���������
	 * 
	 * @param request
	 * @return xml
	 */
	public static String processRequest(HttpServletRequest request) {
		// xml��ʽ����Ϣ����
		String respXml = null;
		//Ĭ�Ϸ����ı���Ϣ����
		String respContent=null;
		try {
			// ����parseXml��������������Ϣ
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// ���ͷ��ʺ�
			String fromUserName = requestMap.get("FromUserName");
			// ������΢�ź�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			// �¼�����
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					textMessage.setContent("���ã���ӭ��ע΢�ŷ���ƽ̨��");
					// ����Ϣ����ת����xml
					respXml = MessageUtil.messageToXml(textMessage);
				}
				// ȡ������
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO �ݲ�������
				}
				// �Զ���˵�����¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_SCANCODEWAITMSG)) {
					// �¼�KEYֵ���봴���˵�ʱ��keyֵ��Ӧ
					String eventKey = requestMap.get("EventKey");
					// ����keyֵ�ж��û�����İ�ť
					if (eventKey.equals("1")) {
						String scanResult=requestMap.get("ScanResult");
						String scanType=requestMap.get("ScanType");
						
						respContent="ɨ�����ͣ�"+scanType+"\n"+"ɨ����:"+scanResult;
						//respContent="received.1";
						
						textMessage.setContent(respContent);
						respXml = MessageUtil.messageToXml(textMessage);
					}
				}
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					// �¼�KEYֵ���봴���˵�ʱ��keyֵ��Ӧ
					String eventKey = requestMap.get("EventKey");
					// ����keyֵ�ж��û�����İ�ť
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
