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

		<title>扫码盘点</title>

		<!-- <link type="text/css" rel="stylesheet" href="css/style.css" />
		<link type="text/css" rel="stylesheet" href="css/jquery.mmenu.all.css" /> -->
		
		<link rel="stylesheet" type="text/css" href="css/jquery-easyui-1.3.2/themes/default/easyui.css" />
    	<link rel="stylesheet" type="text/css" href="css/jquery-easyui-1.3.2/themes/icon.css" />
    	<link rel="stylesheet" type="text/css" href="css/jquery-easyui-1.3.2/demo/demo.css" />

		<!-- <script type="text/javascript" src="js/jquery.min.js"></script> -->
		<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
		<!-- <script type="text/javascript" src="js/jquery.mmenu.min.all.js"></script> -->
		<script type="text/javascript" src="js/gmap3.min.js"></script>
		<script type="text/javascript" src="js/o-script.js"></script>
	    <%
			//获取调用JSSDK所需参数 2016-07-12
			Map<String, String> ret = new HashMap<String, String>();
			//String url="http://scanqrcode.duapp.com/scanqrcode.jsp";
			String url = request.getScheme()+"://"+ request.getServerName()+"/sauthServlet"/* request.getRequestURI() */ +"?"+request.getQueryString() ; 
			//String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()/*  +"?"+request.getQueryString()  */; 
			ret = JsSignUtil.sign(url);
		%>
		<script type="text/javascript">
			/* function sendData(){
				var  code=document.getElementById("id_securityCode_input").value;
				var  openid=$("#receiveOpenId").text();
				window.location.href="http://10.10.64.20:8989/WXSearch/WX_PANDIAN?code="+code+"&openid="+openid;
				//window.location.href="http://192.168.1.250:80/WXSearch/WX_PANDIAN?code="+code+"&openid="+openid;
				
				//window.location.href="http://www.baidu.com";
			} */
			function init(){
				getpdserial();
			} 
			
			function getpdserial(){ 
	 			var  openid=$("#receiveOpenId").text();
	 			if(openid==null)
	 			{
		 			for(var i=0;i<2000;i++)
		 			{
		 			}
		 			openid=$("#receiveOpenId").text();
	 			}
	 			if(openid!=null){
					//alert(openid);	
					//alert();
					$.ajax({
						type:"get",
						url:"http://192.168.1.250:80/WXSearch/ProcessCallback",
						jsonp: "jsoncallback",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
						dataType:"jsonp", 
						data:"openid="+openid+"&jsonpcallback=?",
						
						success:function(json){
									// alert(json.length);
				                    var sel = document.getElementById("pdserial");
				                    for (var i = 0; i < json.length; i++) {
				                        var op = document.createElement("option");
				                        op.value = json[i].id;
				                        op.text = json[i].name;
				                        sel.appendChild(op);
				                    }
				               }, 
				         error: function (msg) {
				                 alert("Error");
				              }
							//$("#pdserial").append(result);
					});
					//alert("adsfasdf---");
				}else{
					var msg="用户信息尚未获取成功，请关闭窗口点击“用户绑定”重试！";
					alert(msg);
				}
			
        	}
		</script>
	</head>

	<body class="o-page" onload="init()">
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
		
		<div id="page" >
			<h2 class="title" align="center"style="font-size:30px">扫码盘点页面</h2>
			<div id="content" style="height: 50px;text-align: center;">
				<table style="align-content:center;text-align: center;">
			        <tr>
			            <td>
			                <span style="font-size:14px;width: 20px;height: 40px;">请选择盘点单： </span>
			            <td>
			                <select name="pdserial" id="pdserial" style="width: 140px;height: 40px;"></select>
			            </td>
			        </tr>
        		</table>
				
				<!-- <input style="display:none" type="text" id="id_securityCode_input"/> -->
				<!-- <button style="width: 100px;height: 30px;" id="Submit" onclick="sendData()">盘点</button> --> 
			</div>
			<div style="text-align: center;">
			       <button style="text-align: center;align-content:center;font-size:14px;width: 100px;height: 40px;" id="scanQRCode" >扫码盘点</button>
			</div>
		</div>
	</body>
	
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
                    var code = res.resultStr; // 当needResult 为 1 时，扫码返回的结果  
                    /* var tempArray=url.split('?');
                    var tempNum=tempArray[1]; */
                    //document.getElementById("scanQRCode").value = result;//将扫描的结果赋予到jsp对应值上  
                    //alert(result);
                    //var subResult=result.substring(7);
                    //$("#id_securityCode_input").val(result);
                    //document.getElementById("id_securityCode_input").value = result;
                    
                    var t = document.getElementById("pdserial")
		            var pdserial = t.options[t.selectedIndex].value;
					var  openid=$("#receiveOpenId").text();
		
		            //alert(pdserial);
		            
		            $.ajax({

		                type: "get",
		
		                url: "http://192.168.1.250:80/WXSearch/GetAsset_Detail", 
		
		                dataType: "jsonp",
		
		                jsonp: "jsonpcallback",   
		
		                data: "code=" + code + "&jsonpcallback=?", 
		
		                success: function (json) {
		
		                    $.messager.confirm("确定要盘点该资产吗？","资产名称："+ json, function (r) {
		                        if (r)
		                            PDData_Set(openid, pdserial, code);

		                    });
		                }
		             });
		            
	                }  
	            });  
        };
        function PDData_Set(openid,pdserial,code)
        {
        
            $.ajax({

                type: "get",

                url: "http://192.168.1.250:80/WXSearch/WX_PDADD", // 这个就是不同于当前域的一个URL地址，这里单纯演示，所以同域

                dataType: "jsonp",

                jsonp: "jsonpcallback",  // 指定回调函数，这里名字可以为其他任意你喜欢的，比如callback，不过必须与下一行的GET参数一致

                data: "openid=" + openid + "&PDserial="+pdserial+"&code="+code+"&jsonpcallback=?", // jsonpcallback与上面的jsonp值一致

                success: function (json) {
                    //alert(json);
                    if (json == "success")
                        $.messager.alert("提示", "盘点成功！", "ok");
                        
                    if (json == "deny")
                        $.messager.alert("提示", "不可重复盘点该资产，或者当前扫描资产未录入系统，请核对!", "error");
                }
            });
        }
	</script>
</html>