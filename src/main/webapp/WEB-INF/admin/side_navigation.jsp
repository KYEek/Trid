<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="adminName" value="${sessionScope.adminName}" />
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${ctxPath}/css/admin/side_navigation.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<div id="admin_side_navigation">
	<div id="logo_container">
		<img src="${ctxPath}/images/logo/logo.svg" width="200" /> <img src="${ctxPath}/images/logo/admin_logo.svg" width="100" />
	</div>

	<div id="admin_menu_container">
		<a href="productManage.trd"><span class="admin_menu">상품 관리</span></a> <a href="#"><span class="admin_menu">주문 관리</span></a> <a href="#"><span class="admin_menu">사용자 관리</span></a> <a href="#"><span
			class="admin_menu">질문 관리</span></a>
	</div>

	<form name="logout_frm">
		<span id="profile">${adminName} 님</span>
		<button type="button" id="logout_button">로그아웃</button>
	</form>
</div>
