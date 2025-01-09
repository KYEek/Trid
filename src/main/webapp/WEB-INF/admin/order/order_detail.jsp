<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 주문 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/order_detail.css">
</head>

<body>
	<%-- OrderDTO --%>
	<c:set var="orderDTO" value="${requestScope.orderDTO}" />
	<%-- MemberDTO --%>
	<c:set var="memberDTO" value="${requestScope.orderDTO.memberDTO}" />
	<%-- AddressDTO --%>
	<c:set var="addrDTO" value="${requestScope.orderDTO.addressDTO}" />
	<%-- OrderDetailDTO List --%>
	<c:set var="orderDetailList" value="${requestScope.orderDTO.orderDetailList}" />

	<%-- 상품 상세 --%>
	<div id="container">

		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">

				<div id="detail_header">
					<h2 style="margin-right:20px;">주문 상세조회</h2>
					<button class="button--ujarak" onclick="location.href='orderManage.trd'">돌아가기</button>
					<button class="button--ujarak" onclick="updateOrder(${orderDTO.pkOrderNo})" style="width:140px;">주문 상태 수정</button>
				</div>

				<div id="detail_container">
					
					<div id="order_summary_container">
						<div class="item">
							<span>주문번호</span> 
							<span class="value_span">${orderDTO.pkOrderNo}</span>
						</div>
						<div class="item">
							<span>주문날짜</span> 
							<span class="value_span">${orderDTO.orderDate}</span>
						</div>
						<div class="item">
							<span>주문상태</span>
							<div style="width:250px;">
								<select id="order_status_select">
									<option value="0">결제완료</option>
									<option value="1">상품준비</option>
									<option value="2">배송중</option>
									<option value="3">배송완료</option>
								</select>
							</div>
						</div>

						<div class="item">
							<span>구매자명</span> 
							<span class="value_span">${memberDTO.member_name}</span>
						</div>
	
						<div class="item">
							<span>배송지</span> 
							<span class="value_span">${addrDTO.address}</span> 
						</div>
					</div>
					
					<div id="order_container">
						<table class="table">
							<thead>
								<tr>
									<th></th>
									<th>상품번호</th>
									<th>상품명</th>
									<th>사이즈</th>
									<th>개수</th>
									<th>단가</th>
									<th>총액</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${orderDetailList}" var="orderDetailDTO">
									<tr class="product_item">
										<td><img width=40 src="${ctxPath}/${orderDetailDTO.productImagePath}" /></td>
										<td>${orderDetailDTO.productNo}</td>
										<td>${orderDetailDTO.productName}</td>
										<td>
											<c:if test="${orderDetailDTO.productSize == 0}">S</c:if>
											<c:if test="${orderDetailDTO.productSize == 1}">M</c:if>
											<c:if test="${orderDetailDTO.productSize == 2}">L</c:if>
											<c:if test="${orderDetailDTO.productSize == 3}">XL</c:if>
										</td>
										<td>${orderDetailDTO.orderDetailQuantity}</td>
										<td><fmt:formatNumber value="${orderDetailDTO.productPrice}" pattern="#,###" /></td>
										<td><fmt:formatNumber value="${orderDetailDTO.productPrice * orderDetailDTO.orderDetailQuantity}" pattern="#,###" /></td>
					
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>	
					
					<div id="total_price_container">
						<c:if test="${orderDTO.orderTotalPrice < 50000}">
							<h4 style="margin-right : 30px;">배송비 &#8361;3,000</h4>
						</c:if>
						<h3>total &#8361;<fmt:formatNumber value="${orderDTO.orderTotalPrice}" pattern="#,###" /></h3>		
					</div>
				</div>

			</div>

	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			
			const orderStatus = ${orderDTO.orderStatus};
			
			$("select#order_status_select").val(orderStatus);
			
			$(document).on("click","tr.product_item", function(e) {
				const productNo = $(this).find("td").eq(1).text();
				location.href = "productDetail.trd?productNo=" + productNo;
			});
			
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