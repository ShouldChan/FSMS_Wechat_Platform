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
 * ��Ȩ��Ļص�������
 * @author Administrator->cxj
 * @date 2016-07-22
 *
 */
public class OAuthServlet extends HttpServlet{
	private static final long serialVersionUID=-1847238807216447030L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		//TODO ͳһΪutf��8����
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//�û�ͬ����Ȩ���ܻ�ȡ��code
		String code=request.getParameter("code");
		//�û�ͬ����Ȩ
		if(!"authdeny".equals(code)){
			//��ȡ��ҳ��Ȩaccess_token
			WeixinOauth2Token weixinOauth2Token = 
					AdvancedUtil.getOauth2AccessToken("wxe7b97a07133763e9", "0526dbeaeaf0e30198bd93fe6399b5b6", code);
			//��ҳ��Ȩ�ӿڷ���ƾ֤
			String accessToken =weixinOauth2Token.getAccessToken();
			//�û���ʶ
			String openId=weixinOauth2Token.getOpenId();
			//��ȡ�û���Ϣ
			SNSUserInfo snsUserInfo = AdvancedUtil.getSNSUserInfo(accessToken, openId);
			//����Ҫ���ݵĲ���
			request.setAttribute("snsUserInfo", snsUserInfo);
		}
		//��ת��ɨ��ҳ��
		request.getRequestDispatcher("scanqrcode.jsp").forward(request, response);
		
	}

}
