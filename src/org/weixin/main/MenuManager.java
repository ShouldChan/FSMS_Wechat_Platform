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

/*菜单管理器类
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
	 * 定义菜单结构
	 * 
	 * @return
	 * */

	private static Menu getMenu() {


		ViewButton mainbtn1 = new ViewButton();
		mainbtn1.setName("资产识别");
		mainbtn1.setType("view");
		mainbtn1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton mainbtn2 = new ViewButton();
		mainbtn2.setName("扫码盘点");
		mainbtn2.setType("view");
		mainbtn2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FsauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		ViewButton mainbtn3=new ViewButton();
		mainbtn3.setName("用户绑定");
		mainbtn3.setType("view");
		mainbtn3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FuauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		
		
		/*ViewButton mainbtn3=new ViewButton();
		mainbtn3.setName("测试");
		mainbtn3.setType("view");
		mainbtn3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9" +
				"&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FuauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
		*/
		
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainbtn1, mainbtn2, mainbtn3 });

		return menu;
	}

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wxe7b97a07133763e9";
		// 第三方用户唯一凭证密钥
		String appSecret = "0526dbeaeaf0e30198bd93fe6399b5b6";

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
	}
}
