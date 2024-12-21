<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 사이드 네비게이션 --%>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/side_navigation.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />

<div id="admin_side_navigation">
	<%-- 로고 --%>
	<div id="logo_container">
		<img src="${ctxPath}/images/logo/logo.svg" width="200" /> <img src="${ctxPath}/images/logo/admin_logo.svg" width="100" />
	</div>

	<%-- 관리자 메뉴 --%>
	<div id="admin_menu_container">
		<a href="productManage.trd"><span class="admin_menu">상품 관리</span></a> 
		<a href="#"><span class="admin_menu">주문 관리</span></a>
		<a href="#"><span class="admin_menu">사용자 관리</span></a> 
		<a href="#"><span class="admin_menu">질문 관리</span></a>
	</div>

	<%-- 로그아웃 --%>
	<form name="logout_frm">
		<span id="profile">${adminName} 님</span>
		<button type="button" id="logout_button">로그아웃</button>
	</form>
</div>
