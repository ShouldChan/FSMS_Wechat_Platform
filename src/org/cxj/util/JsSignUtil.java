package org.cxj.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.cxj.pojo.Token;

/** 
 * 官方给的使用js的验证工具 
 * @author Administrator->cxj 
 * @date1 2016-07-05
 * @date2 2016-07-08
 * 
 */  
public class JsSignUtil {
	public static Token token = null;
    public static String time = null;
    public static String jsapi_ticket = null;
    public static String appId="wxe7b97a07133763e9";
    public static String appSecret="0526dbeaeaf0e30198bd93fe6399b5b6 ";
    /**
     * 
     * @param appId   公账号appId
     * @param appSecret
     * @param url    当前网页的URL，不包含#及其后面部分
     * @return
     */
    /*public static String getParam(String appId,String appSecret){
        if(token == null){
            token = CommonUtil.getToken(appId, appSecret);
            jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
            time = getTime();
        }else{
            if(!time.substring(0, 13).equals(getTime().substring(0, 13))){ //每小时刷新一次
                token = null;
                token = CommonUtil.getToken(appId, appSecret);
                jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
                time = getTime();
            }
        }
         
        String url = getUrl();
         
        Map<String, String> params = sign(jsapi_ticket, url);
        params.put("appid", appId);
         
        JSONObject jsonObject = JSONObject.fromObject(params);  
        String jsonStr = jsonObject.toString();
        System.out.println(jsonStr);
        return jsonStr;
    }
    
    private static String getUrl(){
        HttpServletRequest request = ServletActionContext.getRequest();
         
        StringBuffer requestUrl = request.getRequestURL();
         
        String queryString = request.getQueryString();
        String url = requestUrl +"?"+queryString;
        return url;
    }*/
     /**
      * 首先需要根据appId和appSercret获取accessToken
      * 然后根据accessToken获取jsapiTicket
      * @param jsapi_ticket
      * @param url
      * @return
      */
    public static Map<String, String> sign(/*String jsapi_ticket, */String url) {
    	//获取accessToken
    	/*if(token == null){
            token = CommonUtil.getToken(appId, appSecret);
            jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
            time = getTime();
        }else{
            if(!time.substring(0, 13).equals(getTime().substring(0, 13))){ //每小时刷新一次
                token = null;
                token = CommonUtil.getToken(appId, appSecret);
                jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
                time = getTime();
            }
        }*/
    	token = CommonUtil.getToken(appId, appSecret);
        jsapi_ticket = CommonUtil.getJsApiTicket(token.getAccessToken());
        
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String str;
        String signature = "";
 
        //注意这里参数名必须全部小写，且必须有序
        str = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
 
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
 
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        
        System.out.println("1.url="+ret.get("url"));
        System.out.println("2.原始jsapi_ticket="+jsapi_ticket);
        System.out.println("3.处理后jsapiticket="+ret.get("jsapi_ticket"));
        System.out.println("4.nonceStr="+ret.get("nonceStr"));
        System.out.println("5.timestamp="+ret.get("timestamp"));
        System.out.println("6.signature="+ret.get("signature"));
        
 
        return ret;
    }
 
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
 
    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }
 
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
     
    //获取当前系统时间 用来判断access_token是否过期
    public static String getTime(){
        Date dt=new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }
    /*public static void main(String[] args) {
        String jsapi_ticket = "asdfasdfae5u4RbEYQn7pNQMPrfzl8lJNb1foLDa3HIwI3BRMkQmSO_5F64VFa75uURcq6Uz7QHgA";
        String url = "http://scanqrcode.duapp.com/scanqrcode.jsp";
        Map<String, String> ret = sign(jsapi_ticket, url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    };*/
}
