<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 메인 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Admin Main</title>
<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/admin_main.css">

<%-- js --%>
<script type="text/javascript" src="${ctxPath}/js/jquery-3.7.1.min.js"></script>
</head>

<body>
	<%-- 관리자명 --%>
	<c:set var="adminName" value="${sessionScope.adminName}" />
	
	<div id="main_container">
		<div id="header">
			<%-- 로고 --%>
			<div id="logo_container">
				<img src="${ctxPath}/images/logo/logo.svg" width="200" /> 
				<img src="${ctxPath}/images/logo/admin_logo.svg" width="100" />
			</div>

			<%-- 프로필 --%>
			<div id="profile_container">
				<span id="profile">${adminName} 님</span>
				<%-- 로그아웃 폼 --%>
				<form name="logout_frm">
					<button type="button" id="logout_button">로그아웃</button>
				</form>
			</div>
		</div>

		<%-- 관리자 메뉴 --%>
		<div id="admin_menu_container">
			<a href="productManage.trd"><span class="admin_menu">상품 관리</span></a> 
			<a href="orderManage.trd"><span class="admin_menu">주문 관리</span></a> 
			<a href="memberManage.trd"><span class="admin_menu">사용자 관리</span></a> 
			<a href="boardManage.trd"><span class="admin_menu">질문 관리</span></a>
		</div>

		<%-- 통계 --%>
		<div class="dashboard_container"></div>
		<%-- 통계 --%>
		<div class="dashboard_container"></div>
	</div>
	
	<script>
		$(document).ready(function () {
		    // 로그아웃 이벤트 처리
		    $(document).on("click", "button#logout_button", () => {
		        const frm = document.logout_frm;
		        frm.method = "post";
		        frm.action = "logout.trd";
		        frm.submit();
		    });
		});
	</script>

</body>

</html>