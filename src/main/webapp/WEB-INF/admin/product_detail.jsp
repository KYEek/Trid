<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 상품 상세</title>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">
<script src="${ctxPath}/js/admin/product_detail.js"></script>
</head>
<body>
	<c:set var="productDTO" value="${requestScope.productDTO}" />

	<div id="product_manage_container">
		<%@ include file="side_navigation.jsp"%>
		<div style="display:flex; flex-direction:column">
			<span>${productDTO.productNo}</span> 
			<span>${productDTO.categoryName}</span>
			<%-- 사이즈 분류 --%>
			<c:choose>
				<c:when test="${productDTO.size == 0}">
					<span>S</span>
				</c:when>
				<c:when test="${productDTO.size == 1}">
					<span>M</span>
				</c:when>
				<c:when test="${productDTO.size == 2}">
					<span>L</span>
				</c:when>
				<c:otherwise>
					<span>XL</span>
				</c:otherwise>
			</c:choose>

			<span>${productDTO.productName}</span> 
			<span>${productDTO.explanation}</span> 
			<span>${productDTO.price}</span> 
			<span>${productDTO.inventory}</span> 
			<span>${productDTO.registerday}</span> 
			<span>${productDTO.updateday}</span>

			<c:choose>
				<c:when test="${productDTO.status == 0}">
					<span>비활성화</span>
				</c:when>
				<c:otherwise>
					<span>활성화</span>
				</c:otherwise>
			</c:choose>

			<span>${productDTO.colorDTO.colorName}</span> 

		</div>
		<div></div>
	</div>

</body>
</html>