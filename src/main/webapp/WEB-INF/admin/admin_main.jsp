<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<head>
<meta charset="UTF-8">
<title>Admin Main</title>
<link rel="stylesheet" href="${ctxPath}/css/admin/admin_main.css">
<script type="text/javascript" src="${ctxPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${ctxPath}/js/admin/admin_main.js"></script>

</head>

<body>
	<c:set var="adminName" value="${sessionScope.adminName}" />
	
	<div id="main_container">
		<div id="header">
			<div id="logo_container">
				<img src="${ctxPath}/images/logo/logo.svg" width="200" /> 
				<img src="${ctxPath}/images/logo/admin_logo.svg" width="100" />
			</div>

			<div id="profile_container">
				<span id="profile">${adminName} 님</span>
				<form name="logout_frm">
					<button type="button" id="logout_button">로그아웃</button>
				</form>
			</div>
		</div>

		<div id="admin_menu_container">
			<a href="productManage.trd"><span class="admin_menu">상품 관리</span></a> 
			<a href="#"><span class="admin_menu">주문 관리</span></a> 
			<a href="#"><span class="admin_menu">사용자 관리</span></a> 
			<a href="#"><span class="admin_menu">질문 관리</span></a>
		</div>

		<div class="dashboard_container"></div>

		<div class="dashboard_container"></div>
	</div>

</body>

</html>