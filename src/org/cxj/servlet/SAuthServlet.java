package org.cxj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cxj.pojo.SNSUserInfo;
import org.cxj.pojo.WeixinOauth2Token;
import org.cxj.util.AdvancedUtil;
/**
 * 实现条件查询的授权访问并get用户信息
 * @author Administrator->cxj
 * @date 2016-08-02
 *
 */
public class SAuthServlet extends HttpServlet{
	private static final long serialVersionUID=-1847238807216447030L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		//TODO 统一为utf—8调试
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//用户同意授权后能获取到code
		String code=request.getParameter("code");
		//用户同意授权
		if(!"authdeny".equals(code)){
			//获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = 
					AdvancedUtil.getOauth2AccessToken("wxe7b97a07133763e9", "0526dbeaeaf0e30198bd93fe6399b5b6", code);
			//网页授权接口访问凭证
			String accessToken =weixinOauth2Token.getAccessToken();
			//用户标识
			String openId=weixinOauth2Token.getOpenId();
			//获取用户信息
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
			//设置要传递的参数
			request.setAttribute("snsUserInfo", snsUserInfo);
		}
		//跳转到扫码页面
		request.getRequestDispatcher("testJson.jsp").forward(request, response);
		
	}

}
