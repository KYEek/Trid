<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header/hamburger.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header/header.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>


	<div class="header">
		<!-- 헤더 카테고리 메뉴 버튼 -->
		<div class="header-menu-container">
			<div class="menu-trigger">
				<span></span> <span></span>
			</div>
 			<jsp:include page="/WEB-INF/category/hamburger_category.jsp" />		
 		</div>

		<!-- 로고 -->
		<div class="header-logo-container">
			<div class="header-logo">
				<!-- 로고 하이퍼링크 -->
				<a href="${pageContext.request.contextPath}">
					<img src="${pageContext.request.contextPath}/images/logo/logo.svg" />
				</a>	
			</div>
		</div>

		<!-- 검색창 -->
		<div class="header-search-container">
			<div class="search-bar">
				<a class="search-bar-link" href="${pageContext.request.contextPath}/product/search.trd">검색</a>
			</div>
		</div>

		<!-- 네비게이션 -->
		<div class="header-links">
		<c:if test="${empty sessionScope.loginuser}">
			<a class="header-link" style="color:black" href="${pageContext.request.contextPath}/login.trd">로그인</a> 
		</c:if>
		<c:if test="${not empty sessionScope.loginuser}">
			<a class="header-link" style="color:black" href="${pageContext.request.contextPath}/member/mypage.trd">${(sessionScope.loginuser).member_name}</a> 
		</c:if>
		<c:if test="${not empty sessionScope.loginuser}">	
			<a class="header-link" style="color:black" href="${pageContext.request.contextPath}/basket.trd">바스켓백</a>
		</c:if>
			<a class="header-link" style="color:black" href="${pageContext.request.contextPath}/board/list.trd" >Q&A</a>
		
		<c:if test="${not empty sessionScope.loginuser}">
			<a class="header-link" style="color:black" href="${pageContext.request.contextPath}/login/logout.trd" onclick="return confirm('정말 로그아웃 하시겠습니까?')">로그아웃</a> 			
		</c:if>
		</div>
	</div>
