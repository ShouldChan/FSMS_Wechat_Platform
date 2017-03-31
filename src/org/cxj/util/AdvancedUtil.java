package org.cxj.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.cxj.pojo.SNSUserInfo;
import org.cxj.pojo.WeixinOauth2Token;
import org.cxj.pojo.WeixinQRCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �߼�������
 * @author Administrator->cxj
 * @date1 2016-07-04
 * @date2 2016-07-22
 * ������ҳ��Ȩ��ȡ�û���Ϣ
 */

public class AdvancedUtil {

	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);

	/**
	 * ��ȡ��ҳ��Ȩƾ֤
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return WeixinOauth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		WeixinOauth2Token wat=null;
		//ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl=requestUrl.replace("APPID", appId);
		requestUrl=requestUrl.replace("SECRET", appSecret);
		requestUrl=requestUrl.replace("CODE",code);
		//��ȡ��ҳ��Ȩƾ֤
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null!=jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("��ȡ��ҳ��Ȩƾ֤ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}
	/**
	 * ˢ����ҳƾ֤
	 * @param appId
	 * @param refreshToken
	 * @return WeixinOauth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken){
		WeixinOauth2Token wat=null;
		//ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl=requestUrl.replace("APPID", appId);
		requestUrl=requestUrl.replace("REFRESH_TOKEN", refreshToken);
		//ˢ����ҳ��Ȩƾ֤
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null!=jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				wat=null;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getString("errmsg");
				log.error("ˢ����ҳ��Ȩƾ֤ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}
	/**
	 * ͨ����ҳ��Ȩ��ȡ�û���Ϣ
	 * @param accessToken
	 * @param openId
	 * @return SNSUserInfo
	 */
	@SuppressWarnings({"deprecation","unchecked"})
	public static SNSUserInfo getSNSUserInfo(String accessToken,String openId){
		SNSUserInfo snsUserInfo=null;
		//ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		//ͨ����ҳ��Ȩ��ȡ�û���Ϣ
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		if(null!=jsonObject){
			try{
				snsUserInfo = new SNSUserInfo();
				// �û��ı�ʶ
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// �ǳ�
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// �Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				// �û����ڹ���
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// �û�����ʡ��
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// �û����ڳ���
				snsUserInfo.setCity(jsonObject.getString("city"));
				// �û�ͷ��
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// �û���Ȩ��Ϣ
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
			}catch(Exception e){
				snsUserInfo=null;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getString("errmsg");
				log.error("��ȡ�û���Ϣʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return snsUserInfo;
	}
	/**
	 * ������ʱ���ζ�ά��
	 * 
	 * @param accessToken �ӿڷ���ƾ֤
	 * @param expireSeconds ��ά����Чʱ�䣬��λΪ�룬��󲻳���1800
	 * @param sceneId ����ID
	 * @return WeixinQRCode
	 */
	public static WeixinQRCode createTemporaryQRCode(String accessToken, int expireSeconds, int sceneId) {
		WeixinQRCode weixinQRCode = null;
		// ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// ��Ҫ�ύ��json����
		String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// ������ʱ���ζ�ά��
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, expireSeconds, sceneId));

		if (null != jsonObject) {
			try {
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObject.getInt("expire_seconds"));
				log.info("������ʱ���ζ�ά��ɹ� ticket:{} expire_seconds:{}", weixinQRCode.getTicket(), weixinQRCode.getExpireSeconds());
			} catch (Exception e) {
				weixinQRCode = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("������ʱ���ζ�ά��ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinQRCode;
	}
	
	/**
	 * �������ô��ζ�ά��
	 * 
	 * @param accessToken �ӿڷ���ƾ֤
	 * @param sceneId ����ID
	 * @return ticket
	 */
	public static String createPermanentQRCode(String accessToken, int sceneId) {
		String ticket = null;
		// ƴ�������ַ
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// ��Ҫ�ύ��json����
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// �������ô��ζ�ά��
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneId));

		if (null != jsonObject) {
			try {
				ticket = jsonObject.getString("ticket");
				log.info("�������ô��ζ�ά��ɹ� ticket:{}", ticket);
			} catch (Exception e) {
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("�������ô��ζ�ά��ʧ�� errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return ticket;
	}

	/**
	 * ����ticket��ȡ��ά��
	 * 
	 * @param ticket ��ά��ticket
	 * @param savePath ����·��
	 */
	public static String getQRCode(String ticket, String savePath) {
		String filePath = null;
		// ƴ�������ַ
		String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET", CommonUtil.urlEncodeUTF8(ticket));
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// ��ticket��Ϊ�ļ���
			filePath = savePath + ticket + ".jpg";

			// ��΢�ŷ��������ص�������д���ļ�
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			log.info("����ticket��ȡ��ά��ɹ���filePath=" + filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("����ticket��ȡ��ά��ʧ�ܣ�{}", e);
		}
		return filePath;
	}


	public static void main(String args[]) {
		// ��ȡ�ӿڷ���ƾ֤
		//String accessToken = CommonUtil.getToken("APPID", "APPSECRET").getAccessToken();
		String accessToken = CommonUtil.getToken("wxb21fe51ea23037c1", "c3709b0ea88c5162c9babfea6232f7e7").getAccessToken();

		/**
		 * ������ʱ��ά��
		 */
		WeixinQRCode weixinQRCode = createTemporaryQRCode(accessToken, 900, 111112);
		// ��ʱ��ά���ticket
		System.out.println(weixinQRCode.getTicket());
		// ��ʱ��ά�����Чʱ��
		System.out.println(weixinQRCode.getExpireSeconds());

		/**
		 * ����ticket��ȡ��ά��
		 */
		String ticket = "gQEg7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2lIVVJ3VmJsTzFsQ0ZuQ0Y1bG5WAAIEW35+UgMEAAAAAA==";
		String savePath = "D:/download";
		// ����ticket��ȡ��ά��
		getQRCode(ticket, savePath);

	}
		
}
