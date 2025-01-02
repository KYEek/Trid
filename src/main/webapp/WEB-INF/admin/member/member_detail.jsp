<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 상품 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 회원 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product_detail.js"></script>
</head>

<body>
	<%-- MemberDTO --%>
	<c:set var="memberDTO" value="${requestScope.memberDTO}" />

	<%-- 상품 상세 --%>
	<div id="product_manage_container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">
		
			<div id="item_container">
			
				<div id="detail_header">
					<h2>회원 상세조회</h2>
					<div id="header_buttons">
						<%-- 돌아가기 버튼을 클릭 시 이전 페이지로 돌아간다. --%>
						<a id="return" href="memberManage.trd">돌아가기</a> 
					</div>
				</div>
	
				<%-- 상세 정보 --%>
				<div id="detail_container">
					<div class="item">
						<span>회원 일련번호</span> 
						<span>${memberDTO.pk_member_no}</span>
						
						<span>회원명</span> 
						<span>${memberDTO.member_name}</span>
						
						<span>이메일</span> 
						<span>${memberDTO.member_email}</span>
						
						<span>전화번호</span> 
						<span>${memberDTO.member_mobile}</span>
					</div>

	
				</div>
			
			</div>
			
		</div>

	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			
			
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