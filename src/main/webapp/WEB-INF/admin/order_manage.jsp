<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 질문 게시판 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trid Order Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/product_manage.css">

<%-- js --%>
</head>

<body>
	<%-- 관리자 명 --%>
	<c:set var="adminName" value="${sessionScope.adminName}" />
	<%-- BoardDTO List --%>
	<c:set var="orderList" value="${requestScope.orderList}" />

	<%-- 질문 리스트 --%>
	<div id="product_manage_container">
	
		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="side_navigation.jsp" %>
		
		<div id="content_container">
			<div id="search_container">
			<form id="sort_frm" name="sort_frm">

				<div id="product_manage_header">
					<h1>Order Manage</h1>
				</div>

				<div id="product_category">
				
					<div class="range">
						<span>기간</span> 
						<input id="date_input" type="date" />&nbsp;~&nbsp;<input id="date_input" type="date" />
					</div>
				

					<div id="sort_box">
						<select id="sort_select" name="sortCategory">
							<option value="0">최신순</option>
							<option value="1">오래된순</option>
							<option value="2">높은 가격순</option>
							<option value="3">낮은 가격순</option>
						</select>

						<button type="button" id="search_button">검색</button>
					</div>
				</div>

			</form>
		</div>

			<table class="table">
				<thead>
					<tr>
						<th scope="col">주문 일련번호</th>
						<th scope="col">회원번호</th>
						<th scope="col">회원명</th>
						<th scope="col">주문상품</th>
						<th scope="col">총 주문가격</th>
						<th scope="col">주문상태</th>
						<th scope="col">주문일자</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty orderList}">
						<c:forEach items="${orderList}" var="orderDTO">
							<tr>
								<td>${orderDTO.pkOrderNo}</td>
								<td>${orderDTO.memberDTO.pk_member_no}</td>
								<td>${orderDTO.memberDTO.member_name}</td>
								
								<td>${orderDTO.firstProductName}
									
									<c:if test="${orderDTO.totalProductDetailCount > 1}">
										외 ${orderDTO.totalProductDetailCount - 1}개 상품
									</c:if>
								
								</td>
								<td>${orderDTO.orderTotalPrice}</td>

								<c:choose>
									<c:when test="${orderDTO.orderStatus == 0}">
										<td>결제완료</td>
									</c:when>
									<c:when test="${orderDTO.orderStatus == 1}">
										<td>상품준비</td>
									</c:when>
									<c:when test="${orderDTO.orderStatus == 2}">
										<td>배송중</td>
									</c:when>
									<c:when test="${orderDTO.orderStatus == 3}">
										<td>배송완료</td>
									</c:when>
								</c:choose>
								
								<td>${orderDTO.orderDate.substring(0, 10)}</td>
								
								<td><button onclick="goOrderDetail(${orderDTO.pkOrderNo})">자세히보기</button></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<div>
				<%@ include file="../paging.jsp" %>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	
		$(document).ready(function() {
			let url = "";
			$(document).on("click", "a.page_button", function () {
				
				const page = $(this).data("page");
	
				location.href = "orderManage.trd?curPage=" + page + url;
				
			});
		});
		
		function goOrderDetail(orderNo) {
			location.href = "orderDetail.trd?orderNo=" + orderNo;
		}
	
	</script>
</body>

</html>