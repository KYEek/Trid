<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 사이드 네비게이션 --%>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/side_navigation.css">
<link rel="stylesheet" href="${ctxPath}/css/admin/button.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />

<div id="admin_side_navigation">
	<div>
		<%-- 로고 --%>
		<div id="logo_container">
			<a href="main.trd" ><img src="${ctxPath}/images/logo/logo_white.svg" width="120" /></a> 
		</div>
	
		<%-- 관리자 메뉴 --%>
		<div id="admin_menu_container">
			<span>Admin Menu</span>
			<a class="menu" href="main.trd">
				<img src="${ctxPath}/images/icon/analysis.svg" width="30" />
				<span class="admin_menu">Analysis</span>
			</a> 
			<a class="menu" href="productManage.trd">
				<img src="${ctxPath}/images/icon/product.svg" width="30" />
				<span class="admin_menu">Product Manage</span>
			</a> 
			<a class="menu" href="orderManage.trd">
				<img src="${ctxPath}/images/icon/cart.svg" width="30" />
				<span class="admin_menu">Order Manage</span>
			</a> 
			<a class="menu" href="memberManage.trd">
				<img src="${ctxPath}/images/icon/user.svg" width="30" />
				<span class="admin_menu">Member Manage</span>
			</a> 
			<a class="menu" href="boardManage.trd">
				<img src="${ctxPath}/images/icon/message.svg" width="30" />
				<span class="admin_menu">Q&A Manage</span>
			</a>
		</div>
	</div>

	<%-- 로그아웃 --%>
	<form id="logout_frm" name="logout_frm">
		<span id="profile">${adminName} 님</span>
		<button type="button" id="logout_button" onclick="location.href='logout.trd';">로그아웃</button>
	</form>
	
	<p style="font-size:11pt;">
	위 프로젝트에 사용된 사진은 <br>
	비영리적 목적이며 <br>
	교육목적으로 포트폴리오를 <br> 
	만드는데 사용되었습니다.</p>
</div>