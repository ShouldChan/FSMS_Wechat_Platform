package org.weixin.main;

import org.cxj.menu.Button;
import org.cxj.menu.ClickButton;
import org.cxj.menu.CommonButton;
import org.cxj.menu.ComplexButton;
import org.cxj.menu.Menu;
import org.cxj.menu.ViewButton;
import org.cxj.pojo.Token;
import org.cxj.util.CommonUtil;
import org.cxj.util.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*�˵���������
 * 
 * @author cxj
 * @date1 2016-04-12
 * @date2 2016-04-13
 * @date3 2016-05-30
 * @date4 2016-07-04
 * 
*/
public class MenuManager {
	private static Logger log=LoggerFactory.getLogger(MenuManager.class);
	
	/*
	 * ����˵��ṹ
	 * 
	 * @return
	 * */

	private static Menu getMenu() {


		ViewButton mainbtn1 = new ViewButton();
		mainbtn1.setName("�ʲ�ʶ��");
		mainbtn1.setType("view");
		mainbtn1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton mainbtn2 = new ViewButton();
		mainbtn2.setName("ɨ���̵�");
		mainbtn2.setType("view");
		mainbtn2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FsauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton mainbtn3=new ViewButton();
		mainbtn3.setName("�û���");
		mainbtn3.setType("view");
		mainbtn3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FuauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		
		/*ViewButton mainbtn3=new ViewButton();
		mainbtn3.setName("����");
		mainbtn3.setType("view");
		mainbtn3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FuauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		*/
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainbtn1, mainbtn2, mainbtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// �������û�Ψһƾ֤
		String appId = "wxe7b97a07133763e9";
		// �������û�Ψһƾ֤��Կ
		String appSecret = "0526dbeaeaf0e30198bd93fe6399b5b6";

		// ���ýӿڻ�ȡƾ֤
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// �����˵�
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());

			// �жϲ˵��������
			if (result)
				log.info("�˵������ɹ���");
			else
				log.info("�˵�����ʧ�ܣ�");
		}
	}
}
