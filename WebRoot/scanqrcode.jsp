<%@ page language="java" import="java.util.*,org.cxj.util.*,org.cxj.pojo.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html" charset="UTF-8"/>
		<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />

		<title>资产识别</title>


		<link type="text/css" rel="stylesheet" href="css/style.css" />
		<link type="text/css" rel="stylesheet" href="css/jquery.mmenu.all.css" />

		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.mmenu.min.all.js"></script>
		<script type="text/javascript" src="js/gmap3.min.js"></script>
		<script type="text/javascript" src="js/o-script.js"></script>
	    <%
			//获取调用JSSDK所需参数 2016-07-12
			Map<String, String> ret = new HashMap<String, String>();
			//String url="http://scanqrcode.duapp.com/scanqrcode.jsp";
			String url = request.getScheme()+"://"+ request.getServerName()+"/oauthServlet"/* request.getRequestURI() */ +"?"+request.getQueryString() ; 
			//String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()/*  +"?"+request.getQueryString()  */; 
			ret = JsSignUtil.sign(url);
		%>
		<script>
			function sendData(){
				var  code=document.getElementById("id_securityCode_input").value;
				var  openid=$("#receiveOpenId").text();
				//window.location.href="http://10.10.64.20:8989/WXSearch/WX_detail?code="+code+"&openid="+openid;
				window.location.href="http://192.168.1.250:80/WXSearch/WX_detail?code="+code+"&openid="+openid;
				
				//window.location.href="http://www.baidu.com";
			}
		</script>
	</head>

	<body class="o-page">
		<% 
			// 获取由OAuthServlet中传入的参数
			SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
			if(null != user) {
		%>
		<div style="display:none"><label id="receiveOpenId"><%=user.getOpenId()%></label></div>
		<%
			}
			else {
				out.print("用户不同意授权,未获取到用户信息！");
			}
		%>
		<!-- <div>
			<iframe id="iframe" name="i" src="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe7b97a07133763e9&redirect_uri=http%3A%2F%2Fscanqrcode.duapp.com%2FoauthServlet&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect"></iframe>
		</div> -->
		<div id="page" >
			<h2 class="title" align="center">资产识别扫码查询页面</h2>
			<div id="content" style="height: 450px;text-align: center;">
				
				<input type="text" id="id_securityCode_input"/>
				<button style="width: 100px;height: 30px;" id="scanQRCode" >扫码</button> 
				<button style="width: 100px;height: 30px;" id="Submit" onclick="sendData()">查询</button> 

			</div>
		</div>
	</body>
	<!-- <pre name="code" class="html"> -->
	
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
		//alert(1);
		//alert(location.href.split('#')[0]);
		wx.config({
      		debug: false,/* 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。 */
      		/*appId: 'wxb21fe51ea23037c1', // 必填，公众号的唯一标识
      		 timestamp: '2016070922', // 必填，生成签名的时间戳
      		nonceStr: '82693e11-b9bc-448e-892f-f5289f46cd0f', // 必填，生成签名的随机串
      		signature: '6a2bf1f5460ab97241021331ecd95b959d4fd66a',// 必填，签名，见附录1
        	jsApiList : [ 'checkJsApi', 'scanQRCode' ]  // 必填，需要使用的JS接口列表，所有JS接口列表见附录2 */
        	appId: 'wxe7b97a07133763e9',  
        	timestamp:'<%=ret.get("timestamp")%>',  
        	nonceStr:'<%=ret.get("nonceStr")%>',  
        	signature:'<%=ret.get("signature")%>',  
        	jsApiList : [ 'checkJsApi', 'scanQRCode' ] 
        });

  		wx.error(function(res) {  
        	alert("出错了：" + res.errMsg); 
    	}); 
  		wx.checkJsApi({
    		jsApiList: ['scanQRCode'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    		success: function(res) {
        		// 以键值对的形式返回，可用的api值true，不可用为false
        		// 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
    		}
		});
		/* 放在wx.ready 里面的内容，会等到相关信息全部加载完毕后才可以被触发。
		如果用户引入的接口是需要点击才会调用的，则放不放在里面都无所谓。比如上面例子的
		接口是用来检验当前配置信息是否合法的，那么这个功能是不需要用户来点击的，当你打开网页
		时，自动就会调用该接口，那么它就必须放在ready里面。再比如我们打算调用的扫一扫，这个必须
		（最好是）是用户进入页面后点击某个按钮才触发，这么就不必放到ready里面。 
		wx.ready(function(){wx.XXX});
		*/
		//扫描二维码  
        document.querySelector('#scanQRCode').onclick = function() {  
           wx.scanQRCode({  
                needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，  
                /* desc : 'scanQRCode desc', */
                scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有  
                success : function(res) {  
                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果  
                    /* var tempArray=url.split('?');
                    var tempNum=tempArray[1]; */
                    //document.getElementById("scanQRCode").value = result;//将扫描的结果赋予到jsp对应值上  
                    //alert(result);
                    //var subResult=result.substring(7);
                    //$("#id_securityCode_input").val(result);
                    document.getElementById("id_securityCode_input").value = result;
                }  
            });  
        };
	</script>
</html>