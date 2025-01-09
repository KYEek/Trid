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
<link rel="stylesheet" href="${ctxPath}/css/admin/member_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>

<body>
	<%-- MemberDTO --%>
	<c:set var="memberDTO" value="${requestScope.memberDTO}" />

	<%-- 상품 상세 --%>
	<div id="container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">
		
			<div id="item_container">
			
				<div id="detail_header">
					<h2 style="margin-right:20px;">회원 상세조회</h2>
					<button type = button class="button--ujarak" onclick="location.href='memberManage.trd'">돌아가기</button>
					<button type = button class="button--ujarak" onclick="location.href='memberLoginHistory.trd?memberNo=${memberDTO.pk_member_no}'" style="width:150px;">
						로그인 이력 조회
					</button>
				</div>
	
				<%-- 상세 정보 --%>
				<div id="detail_container">					
					<div id="member_summary_container">
						<div class="item">
							<span>회원 일련번호</span> 
							<span class="value_span">${memberDTO.pk_member_no}</span>
						</div>
						<div class="item">
							<span>회원명</span> 
							<span class="value_span">${memberDTO.member_name}</span>
						</div>
						<div class="item">
							<span>성별</span> 
							<span class="value_span">
								<c:if test="${memberDTO.member_gender == 0}">여자</c:if>
								<c:if test="${memberDTO.member_gender == 1}">남자</c:if>
							</span>
						</div>
						<div class="item">
							<span>이메일</span>
							<span class="value_span">${memberDTO.member_email}</span>
						</div>

						<div class="item">
							<span>전화번호</span> 
							<span class="value_span">${memberDTO.member_mobile}</span>
						</div>
						
						<div class="item">
							<span>회원 상태</span> 
							<span class="value_span">
								<c:if test="${memberDTO.member_status == 0}">탈퇴</c:if>
								<c:if test="${memberDTO.member_status == 1}">가입</c:if>
							</span>
						</div>
						
						<div class="item">
							<span>휴면 유무</span> 
							<span class="value_span">
								<c:if test="${memberDTO.member_idle == 0}">휴면</c:if>
								<c:if test="${memberDTO.member_idle == 1}">활성화</c:if>
							</span>
						</div>
						
						<div class="item">
							<span>가입일자</span> 
							<span class="value_span">${memberDTO.member_registerday}</span>
						</div>
						
						<div class="item">
							<span>수정일자</span> 
							<span class="value_span">${memberDTO.member_updateday}</span>
						</div>
						
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