<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.cxj.util.*,org.cxj.pojo.*,org.cxj.pojo.SNSUserInfo;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>条件查询用户授权界面</title>
    <meta name="viewport" content="width=device-width,user-scalable=0">
	<style type="text/css">
		*{margin:0; padding:0}
		table{border:1px dashed #B9B9DD;font-size:12pt}
		td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
	</style>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript"> 
		window.onload=function(){ 
 			var  openid=$("#receiveOpenId").text();
 			if(openid==null)
 			{
	 			for(var i=0;i<5000;i++)
	 			{
	 				
	 			}
	 			openid=$("#receiveOpenId").text();
 			}
 			
 			if(openid!=null){
				//alert(openid);
				window.location.href="http://10.10.64.108:8989/WXSearch/WX_Search?openid="+openid;
			}else{
				var msg="由于网络不佳，请关闭窗口点击“条件查询”重试！";
				alert(msg);
			}
		} 
	</script>
	<!-- <script>
			function goSelectQuery(){
				var  openid=$("#receiveOpenId").text();
				alert(openid);
				window.location.href="http://10.10.64.108:8989/WXSearch/WX_Search?openid="+openid;
			}
	</script> -->
  </head>
  
  <body>
    <body>
	<% 
		// 获取由SAuthServlet中传入的参数
		SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
		if(null != user) {
	%>
		<div style="display:none"><label id="receiveOpenId"><%=user.getOpenId()%></label></div>
	<%
		}
		else 
			out.print("用户不同意授权,未获取到用户信息！");
	%>
	<!-- <div id="content" style="height: 450px;text-align: center;">
				<button style="width: 100px;height: 30px;" id="Submit" onclick="goSelectQuery()">条件查询</button> 
	</div> -->
	<!-- <iframe id="iframe" name="i" src="http://scanqrcode.duapp.com/scanqrcode.jsp"></iframe> -->
</body>
  </body>
</html>
