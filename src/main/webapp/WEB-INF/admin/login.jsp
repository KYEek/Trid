<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 관리자 로그인 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trid Admin</title>
<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>

<body>
	<%-- 로그인 --%>
	<div id="login_container">
		<%-- 로고 --%>
		<div id="logo_container">
			<img src="${pageContext.request.contextPath}/images/logo/logo.svg" width="300" /> 
			<img src="${pageContext.request.contextPath}/images/logo/admin_logo.svg" width="150" />
		</div>

		<%-- 로그인 폼 --%>
		<form id="login_form" action="login.trd" method="post">
			<input class="login_input" type="text" name="adminId" placeholder="아이디" /> 
			<input class="login_input" type="password" name="password" placeholder="비밀번호" />

			<button id="login_button" type="submit">로그인</button>
		</form>

	</div>
</body>

</html>