<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 상품 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product_detail.js"></script>
</head>

<body>
	<%-- ProductDTO --%>
	<c:set var="productDTO" value="${requestScope.productDTO}" />
	<%-- ColorDTO List --%>
	<c:set var="colorList" value="${requestScope.productDTO.colorList}" />
	<%-- CategoryDTO --%>
	<c:set var="categoryDTO" value="${requestScope.productDTO.categoryDTO}" />
	<%-- ProductDetailDTO List --%>
	<c:set var="productDetailList" value="${requestScope.productDTO.productDetailList}" />
	<%-- ImageDTO List --%>
	<c:set var="imageList" value="${requestScope.productDTO.imageList}" />

	<%-- 상품 상세 --%>
	<div id="product_manage_container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="side_navigation.jsp"%>

		<div id="main_container">
			<div id="detail_header">
				<h2>상품 상세조회</h2>
				<div>
					<%-- 돌아가기 버튼을 클릭 시 이전 페이지로 돌아간다. --%>
					<a id="return" href="productManage.trd">돌아가기</a> <a href="productUpdate.trd?productNo=${productDTO.productNo}">수정하기</a>
				</div>
			</div>

			<%-- 상세 정보 --%>
			<div id="detail_container">
				<span>상품 일련번호 : ${productDTO.productNo}</span> <span>카테고리 : ${categoryDTO.categoryName}</span> <span>성별 : ${categoryDTO.gender}</span> <span>상의/하의 : ${categoryDTO.type}</span>

				<%-- 사이즈 분류 --%>
				<c:forEach items="${productDetailList}" var="productDetailDTO">

					<div style="display: flex">
						<c:choose>
							<c:when test="${productDetailDTO.size == 0}">
								<span>사이즈 : S //</span>
							</c:when>
							<c:when test="${productDetailDTO.size == 1}">
								<span>사이즈 : M //</span>
							</c:when>
							<c:when test="${productDetailDTO.size == 2}">
								<span>사이즈 : L //</span>
							</c:when>
							<c:otherwise>
								<span>사이즈 : XL //</span>
							</c:otherwise>
						</c:choose>

						<span>재고 : ${productDetailDTO.inventory}</span>
					</div>
				</c:forEach>

				<span>상품명 : ${productDTO.productName}</span> <span>상품설명 : ${productDTO.explanation}</span> <span>가격 : ${productDTO.price}</span> <span>등록일자 : ${productDTO.registerday}</span> <span>수정일자 :
					${productDTO.updateday} </span>

				<%-- 상품 상태 여부 --%>
				<c:choose>
					<c:when test="${productDTO.status == 0}">
						<span>비활성화</span>
					</c:when>
					<c:otherwise>
						<span>활성화</span>
					</c:otherwise>
				</c:choose>

				<%-- 상품 색상 --%>
				<c:forEach items="${colorList}" var="colorDTO">
					<span>${colorDTO.colorName}</span>
					<span>${colorDTO.colorCode}</span>
				</c:forEach>

			</div>

			<%-- 이미 삭제 상태인 상품은 삭제버튼을 비활성화 한다. --%>
			<c:if test="${productDTO.status == 1}">
				<form name="delete_product_frm">
					<input type="hidden" name="productNo" value="${productDTO.productNo}" />
					<button id="delete_product_button" type="button">삭제하기</button>
				</form>
			</c:if>

		</div>

		<div style="height: 700px;">
			<%@ include file="../image_carousel.jsp"%>
		</div>
	</div>


</body>

</html>