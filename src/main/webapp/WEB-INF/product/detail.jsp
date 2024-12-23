<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 페이지</title>


<%-- 공용 CSS --%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/css/bootstrap.min.css">
<%-- 공용 JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js"></script>

<%-- 개인 CSS 및 JS --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/product/detail.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/detail.js"></script>

</head>
<body>

	<jsp:include page="/WEB-INF/header.jsp" />

	<div id="container">
		<div id="img_box">
			<div id="main_img">
				<div class="image-container">
					<div class="product-slide" style="background-color: red"></div>
					<div class="product-slide" style="background-color: blue"></div>
					<div class="product-slide" style="background-color: yellow"></div>
					<div class="product-slide" style="background-color: green"></div>
				</div>
				<div class="touch-overlay"></div>
			</div>

			<div id="img_sidebar">
				<div class="thumbnail" style="background-color: red;"></div>
				<div class="thumbnail" style="background-color: blue;"></div>
				<div class="thumbnail" style="background-color: yellow;"></div>
				<div class="thumbnail" style="background-color: green;"></div>
			</div>
		</div>

		<div id="product_detailbox">
			<div id="top_deatilbox">
				<div id="name">상품명</div>
				<div id="price">가격</div>
				<div id="explanation">상품 설명</div>
			</div>

			<div id="bottom_detailbox">
				<div id="color">색상 || 색상코드</div>
				<div id="colorbox">
					<div class="gratify" style="background-color: blanchedalmond;"></div>
					<div class="gratify" style="background-color: aqua"></div>
				</div>

				<div id="size_bar">
					<input class="size" type="button" value="S"> <input class="size" type="button" value="M"> 
					<input class="size"type="button" value="L"> <input class="size" type="button" value="XL">
				</div>

				<div id="paymentbar">
					<input id="go_basket" class="paymentBtn" type="button" value="장바구니에 추가하기"> 
					<input id="go_payment" class="paymentBtn" type="button" value="바로 결제하기">
				</div>
			</div>
		</div>
	</div>

</body>
</html>