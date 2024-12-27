<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>내 주소</title>

<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/css/main.css"> --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/css/header/hamburger.css"> --%>
<!-- <link rel="stylesheet" -->
<%-- 	href="${pageContext.request.contextPath}/css/header/header.css"> --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/myPage/mypage.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
const addrListStr = `${requestScope.addrList}`;
const addrList = JSON.parse(addrListStr);
</script>

</head>
<body>
<%-- 	<%@include file="../header.jsp"%> --%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/myPage/address.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/mypage/address.js"></script>
	<main>
		<div id="address_header">
			<span>주소</span>
			<div>주소추가</div>
		</div>
		<div id="menu_container">
			<ul id="menu_ul"></ul>
		</div>
	</main>
	<%@include file="../footer.jsp"%>
</body>
</html>