<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<head>
<meta charset="UTF-8">
<title>Trid Product Manage</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/product_manage.css">
<script src="${pageContext.request.contextPath}/js/admin/product_manage.js"></script>
</head>

<body>
	<c:set var="adminName" value="${sessionScope.adminName}" />
	<c:set var="productList" value="${requestScope.productList}" />

	<div id="product_manage_container">
		<%@ include file="side_navigation.jsp" %>
		
		<div id="content_container">
			<div id="search_container"></div>

			<table class="table">
				<thead>
					<tr>
						<th scope="col">상품 일련번호</th>
						<th scope="col">상품 상세 일련번호</th>
						<th scope="col">카테고리</th>
						<th scope="col">상품 사이즈</th>
						<th scope="col">상품명</th>
						<th scope="col">상품 가격</th>
						<th scope="col">재고</th>
						<th scope="col">등록일자</th>
						<th scope="col">수정일자</th>
						<th scope="col">상태</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty productList}">
						<c:forEach items="${productList}" var="productDTO">
							<tr>
								<td>${productDTO.productNo}</td>
								<td>${productDTO.productDetailNo}</td>
								<td>${productDTO.categoryName}</td>

								<%-- 사이즈 분류 --%>
								<c:choose>
									<c:when test="${productDTO.size == 0}">
										<td>S</td>
									</c:when>
									<c:when test="${productDTO.size == 1}">
										<td>M</td>
									</c:when>
									<c:when test="${productDTO.size == 2}">
										<td>L</td>
									</c:when>
									<c:otherwise>
										<td>XL</td>
									</c:otherwise>
								</c:choose>

								<td>${productDTO.productName}</td>
								<td>${productDTO.price}</td>
								<td>${productDTO.inventory}</td>
								<td>${productDTO.registerday}</td>
								<td>${productDTO.updateday}</td>

								<c:choose>
									<c:when test="${productDTO.status == 0}">
										<td>비활성화</td>
									</c:when>
									<c:otherwise>
										<td>활성화</td>
									</c:otherwise>
								</c:choose>
								
								<td> <button>삭제</button> </td>
								<td><button onclick="goProductDetail(${productDTO.productDetailNo})">자세히보기</button></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			
			<div>
				<button>첫페이지</button>
				
				<button>마지막페이지</button>
			</div>
			
		</div>
	</div>
</body>

</html>