<%@ page language="java" import="java.util.*,org.cxj.util.JsSignUtil,org.cxj.pojo.SNSUserInfo" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html" charset="UTF-8"/>
		<meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />

		<title>测试页面</title>


		<link type="text/css" rel="stylesheet" href="css/style.css" />
		<link type="text/css" rel="stylesheet" href="css/jquery.mmenu.all.css" />

		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.mmenu.min.all.js"></script>
		<script type="text/javascript" src="js/gmap3.min.js"></script>
		<script type="text/javascript" src="js/o-script.js"></script>
	    
	</head>
		<body class="o-page">
		<div id="page" >
			
			<div id="content" style="height: 450px;text-align: center;">
				
				<button style="width: 100px;height: 30px;" id="closeWin" onclick="closeWindow()">关闭当前窗口</button>
			</div>
			
		</div>
	</body>
	
	<pre name="code" class="html">
	<%
		Map<String, String> ret = new HashMap<String, String>();
		//String url="http://scanqrcode.duapp.com/scantest.jsp";
		//String jsapiticket="kgt8ON7yVITDhtdwci0qeSlWVa0vmTSfYQdCxnZ2V4jK4wMj6XTKIvu2LBMB3h3-0r4Pa05wr-tEIS7ZZHOi0A";
		String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString() ;
		ret = JsSignUtil.sign(url);
		%>
	<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript">
		
		wx.config({
      		debug: true,/* 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。 */
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
        		// 如：{"checkResult":{"scanQRCode":true},"errMsg":"checkJsApi:ok"}
    		}
		});
		/* 放在wx.ready 里面的内容，会等到相关信息全部加载完毕后才可以被触发。
		如果用户引入的接口是需要点击才会调用的，则放不放在里面都无所谓。比如上面例子的
		接口是用来检验当前配置信息是否合法的，那么这个功能是不需要用户来点击的，当你打开网页
		时，自动就会调用该接口，那么它就必须放在ready里面。再比如我们打算调用的扫一扫，这个必须
		（最好是）是用户进入页面后点击某个按钮才触发，这么就不必放到ready里面。 
		wx.ready(function(){wx.XXX});
		*/
		//关闭当前窗口
		document.querySelector('#closeWin').onclick = function() {
			wx.closeWindow();
		};
		//扫描二维码  
        document.querySelector('#scanQRCode').onclick = function() {  
            wx.scanQRCode({  
                needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，  
                desc : 'scanQRCode desc',
                scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有  
                success : function(res) {  
                    var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果  
                    /* var tempArray=url.split('?');
                    var tempNum=tempArray[1]; */
                    document.getElementById("scanQRCode").value = result;//将扫描的结果赋予到jsp对应值上  
                    alert("扫描成功::扫描码=" + result);
                    var subResult=result.substring(7);
                    $("#id_securityCode_input").val(subResult);
                }  
            });  
        };
	</script>
</html>