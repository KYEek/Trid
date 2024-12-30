<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>
    
<!DOCTYPE html>
<html>
<head>
<title></title>

<!-- 직접 만든 CSS -->
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/category/category_header.css" />


</head>
<body>

	<!-- 헤더 카테고리 -->
	<div class="header_menu" style="margin:0.8%">
	
		<div class="menu_size" style="margin:0 0 0 7%">사이즈</div>
		<div class="menu_price">가격</div>
		<div class="menu_color" style="margin:0 16% 0 0">색상</div>
		
		<div class="menu_all">모두보기</div>
		<div class="menu_price">티셔츠</div>
		<div class="menu_color">셔츠</div>
		<div class="menu_size">맨투맨</div>
		<div class="menu_price">후디</div>
		<div class="menu_color" style="margin:0 30% 0 0">니트</div>
		
		<img src="images/logo/2box.svg" alt="2box" />
		<img src="images/logo/4box.svg" alt="4box" />
		
	</div>
	
</body>
</html>