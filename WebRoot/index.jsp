<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,org.cxj.util.*,org.cxj.pojo.*,org.cxj.pojo.SNSUserInfo;"%>
<html>
<head>
	<title>OAuth2.0网页授权</title>
	<meta name="viewport" content="width=device-width,user-scalable=0">
	<style type="text/css">
		*{margin:0; padding:0}
		table{border:1px dashed #B9B9DD;font-size:12pt}
		td{border:1px dashed #B9B9DD;word-break:break-all; word-wrap:break-word;}
	</style>
	
</head>
<body>
	<% 
		// 获取由OAuthServlet中传入的参数
		SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo"); 
		if(null != user) {
	%>
		<label id="receiveOpenId">您好!<%=user.getOpenId() %></label>
	<%
		}
		else 
			out.print("用户不同意授权,未获取到用户信息！");
	%>
	<!-- <iframe id="iframe" name="i" src="http://scanqrcode.duapp.com/scanqrcode.jsp"></iframe> -->
</body>
</html>