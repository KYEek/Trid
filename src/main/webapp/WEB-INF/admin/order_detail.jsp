<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 주문 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product_detail.js"></script>
</head>

<body>
	<%-- OrderDTO --%>
	<c:set var="orderDTO" value="${requestScope.orderDTO}" />
	<%-- MemberDTO --%>
	<c:set var="memberDTO" value="${requestScope.productDTO.memberDTO}" />
	<%-- AddressDTO --%>
	<c:set var="addrDTO" value="${requestScope.productDTO.addressDTO}" />
	<%-- OrderDetailDTO List --%>
	<c:set var="orderDetailList" value="${requestScope.productDTO.orderDetailList}" />	

	<%-- 상품 상세 --%>
	<div id="product_manage_container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="side_navigation.jsp"%>

		<div id="main_container">
		
			<div id="item_container">
			
				<div id="detail_header">
					<h2>상품 상세조회</h2>
					<div id="header_buttons">
						<%-- 돌아가기 버튼을 클릭 시 이전 페이지로 돌아간다. --%>
						<a id="return" href="orderManage.trd">돌아가기</a> 
					</div>
				</div>
	
				<%-- 상세 정보 --%>
				<div id="detail_container">
					<div class="item">
						<span>주문 일련번호</span> 
						<span>${orderDTO.pkOrderNo}</span>
						
						<span>주문 시간</span> 
						<span>${orderDTO.orderDate}</span>
					</div>
				
					<select id="order_status_select">
					
					
								<option value="0">결제완료</option>
					
					
								<option value="1">상품준비</option>
					
					
								<option value="2">배송중</option>
					
					
								<option value="3">배송완료</option>
					
					</select>
	
					<button onclick="updateOrder(${orderDTO.pkOrderNo})">주문 상태 수정</button>
	
				</div>
			
			</div>
			
		</div>

	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			const orderStatus = ${orderDTO.orderStatus};
			
			$("select#order_status_select").val(orderStatus);
			
		});
		
		function updateOrder(orderNo) {
			if(confirm("주문 상태를 수정하시겠습니까?")) {
				const orderStatus = $("select#order_status_select").val();
				
				$.ajax({
					url : "orderDetail.trd",
					type : "post",
					dataType : "json",
					data : {
						orderNo : orderNo,
						orderStatus : orderStatus
					},
					success: (json) => {
						if (json.message == "success") {
							alert("주문상태 수정을 성공했습니다.");
						}
						else {
							alert("주문상태 수정을 실패했습니다.");
						}
					},
					error: () => {
						alert("주문상태 수정를 실패했습니다.");
					}
				});
			}
		}
	</script>

</body>

</html>