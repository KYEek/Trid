<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 상품 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trid Product Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/product_manage.css">

<%-- js --%>
<script src="${pageContext.request.contextPath}/js/admin/product_manage.js"></script>
</head>

<body>
	<%-- 관리자 명 --%>
	<c:set var="adminName" value="${sessionScope.adminName}" />
	<%-- ProductDTO List --%>
	<c:set var="productList" value="${requestScope.productList}" />

	<%-- 상품 리스트 --%>
	<div id="product_manage_container">
	
		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="side_navigation.jsp" %>
		
		<div id="content_container">
			<div id="search_container">
				<a href="productRegister.trd">상품추가</a>
			</div>

			<table class="table">
				<thead>
					<tr>
						<th scope="col">상품 일련번호</th>
						<th scope="col">카테고리</th>
						<th scope="col">상품명</th>
						<th scope="col">상품 가격</th>
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
								<td>${productDTO.categoryDTO.categoryName}</td>
								<td>${productDTO.productName}</td>
								<td>${productDTO.price}</td>
								<td>${productDTO.registerday.substring(0, 10)}</td>
								<td>${productDTO.updateday.substring(0, 10)}</td>

								<c:choose>
									<c:when test="${productDTO.status == 0}">
										<td>비활성화</td>
									</c:when>
									<c:otherwise>
										<td>활성화</td>
									</c:otherwise>
								</c:choose>
								
								<td><button onclick="goProductDetail(${productDTO.productNo})">자세히보기</button></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<div>
				<%-- 페이징 --%>
				<c:set var="paging" value="${requestScope.pagingDTO}" />

			    <nav class="text-center">
			        <ul class="pagination">
			            <%-- 첫 페이지 --%>
			            <li><a href="productManage.trd?curPage=1" data-page="1"><span aria-hidden="true">&laquo;</span></a></li>
			            <%-- 이전 페이지 --%>
			            <c:if test="${paging.firstPage ne 1}">
			                <li><a href="productManage.trd?curPage=${paging.firstPage-1}" data-page="${paging.firstPage-1}"><span aria-hidden="true">&lt;</span></a></li>
			            </c:if>
			
			            <%-- 페이지 넘버링 --%>
			            <c:forEach begin="${paging.firstPage}" end="${paging.lastPage}" var="i" >
			                <c:if test="${paging.curPage ne i}">
			                    <li><a href="productManage.trd?curPage=${i}" data-page="${i}">${i}</a></li>
			                </c:if>
			                <c:if test="${paging.curPage eq i}">
			                    <li class="active"><a href="#">${i}</a></li>
			                </c:if>
			
			            </c:forEach>
			
			            <%-- 다음 페이지 --%>
			            <c:if test="${paging.lastPage ne paging.totalPageCount}">
			                <li><a href="productManage.trd?curPage=${paging.lastPage+1}" data-page="${paging.lastPage+1}"><span aria-hidden="true">&gt;</span></a></li>
			            </c:if>
			
			           	<%-- 마지막 페이지 --%>	
			            <li><a href="productManage.trd?curPage=${paging.totalPageCount}" data-page="${paging.totalPageCount}"><span aria-hidden="true">&raquo;</span></a></li>
			        </ul>
			    </nav>
			</div>
		</div>
	</div>
</body>

</html>