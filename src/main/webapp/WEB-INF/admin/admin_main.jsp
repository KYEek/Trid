
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />
<%-- BoardDTO List --%>
<c:set var="boardList" value="${requestScope.boardList}" />
<%-- selectWeekPayment Map --%>
<c:set var="selectWeekPayment" value="${requestScope.selectWeekPayment}" />
<%-- weekEmptyInventoryList --%>
<c:set var="weekEmptyInventoryList" value="${requestScope.weekEmptyInventoryList}" />
<%-- weekUnansweredQuestionList --%>
<c:set var="weekUnansweredQuestionList" value="${requestScope.weekUnansweredQuestionList}" />

<%-- 질문 게시판 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trid Board Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/admin_main.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manage.css">

<%-- js --%>
<script src="${pageContext.request.contextPath}/js/admin/util.js"></script>
</head>
<body>

	<div class="main_container">
		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="side_navigation.jsp"%>

		<div class="content_container">
			<div style="padding : 10px; border : solid 1px black; width:500px;">
				<span>일주일간 주문 내역</span>
				<div style="display: flex;">
					<div>
						<span>이번주 결제완료</span>${selectWeekPayment.paid}<c:if test="${selectWeekPayment.paid == null}">0</c:if>
					</div>
					<div style="margin-left : 20px;">
						<span>이번주 상품준비</span>${selectWeekPayment.preparing}<c:if test="${selectWeekPayment.preparing == null}">0</c:if>
					</div>
					<div style="margin-left : 20px;">
						<span>이번주 배송중</span>${selectWeekPayment.shipping}<c:if test="${selectWeekPayment.shipping == null}">0</c:if>
					</div>
					<div style="margin-left : 20px;">
						<span>이번주 배송완료</span>${selectWeekPayment.delivered}<c:if test="${selectWeekPayment.delivered == null}">0</c:if>
					</div>
				</div>
			</div>

			<div style="margin-top:30px; width:100%; display:flex; justify-content:center;">
				<div>
					<span>빈 재고 알림</span>
					<div class="table-responsive" style="width: 40vw; height: 300px; overflow-y:scroll; margin-right:50px;">
						<table class="table table-sm">
							<thead>
								<tr>
									<th>상품번호</th>
									<th>상품명</th>
									<th>사이즈</th>
									<th>수정일자</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${weekEmptyInventoryList}" var="weekEmptyInventory">
									<tr>
										<td>${weekEmptyInventory.productNo}</td>
										<td>${weekEmptyInventory.productName}</td>
										<td><c:if test="${weekEmptyInventory.size == 0}">S</c:if> 
										<c:if test="${weekEmptyInventory.size == 1}">M</c:if>
										<c:if test="${weekEmptyInventory.size == 2}">L</c:if> 
										<c:if test="${weekEmptyInventory.size == 3}">XL</c:if></td>
										<td>${weekEmptyInventory.updateday}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div>
					<span>이번주 미답변 질문</span>
					
					<div class="table-responsive" style="width: 40vw; height: 300px; overflow-y:scroll;">
						<table class="table table-sm">
							<thead>
								<tr>
									<th>질문번호</th>
									<th>작성자</th>
									<th>제목</th>
									<th>등록일자</th>
								</tr>
							</thead>
							<tbody>
							<!-- keys(questionNo, memberName, title, registerday) -->
								<c:forEach items="${weekUnansweredQuestionList}" var="weekUnansweredQuestion">
									<tr>
										<td>${weekUnansweredQuestion.questionNo}</td>
										
										<td>${weekUnansweredQuestion.memberName}</td>
										
										<td>${weekUnansweredQuestion.title}</td>
										
										<td>${weekUnansweredQuestion.registerday}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div style="display:flex; justify-content:center; ">
				<div style="display: flex; width: 40vw; margin-right:50px;">
					<canvas id="week-login-user-chart" width="600" height="300"></canvas>
				</div>
				
				<div style="display: flex; width: 40vw;">
					<canvas id="daily-order-chart" width="600" height="300"></canvas>
				</div>
			</div>
			
			<div style="margin-top:50px; display:flex; justify-content:center;">
				<div style="display: flex; width: 40vw; margin-right:60px;">
					<canvas id="monthly-order-chart" width="600" height="300"></canvas>
				</div>
				
				<div style="display: flex; width: 40vw;">
					<canvas id="year-order-chart" width="600" height="300"></canvas>
				</div>
			</div>

		</div>

		<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>

		<script>
			// 일주일간 날짜 저장
			let date = new Date();
			
			// 날짜 저장 배열
			const dateArr = [];
			
			dateArr.push(date.toLocaleDateString('en-CA'));
			
			for(let i=0; i<6; i++){
				date.setDate(date.getDate() - 1);
				dateArr.push(date.toLocaleDateString('en-CA'));
			}
			
			// JSON 배열
			/*
				0 : {
					count : 13
					logindate : "2024-12-12" 
					}
			*/
			const jsonWeekLoginUser = ${requestScope.jsonWeekLoginUser};
			
			const jsonDailySales = ${requestScope.jsonDailySales};
			
			const jsonMonthlySales = ${requestScope.jsonMonthlySales};
			
			const jsonYearSales = ${requestScope.jsonYearSales};
			
			// console.log(jsonWeeklySales);
			
			
			// 일주일간 사용자 접속 카운트 배열
			let weekLoginUserArr = [0, 0, 0, 0, 0, 0, 0];
			
			let dailySalesArr = [0, 0, 0, 0, 0, 0];
			
			let monthlySalesArr = [];
			let month = [];
			
			let yearSalesArr = [];
			let year = [];
			
			// logindate와 dateArr의 날짜가 동일한 경우 count 값을 weekLoginUserArr에 저장
			jsonWeekLoginUser.forEach((item, index) => {
				for(let i = 0; i < 7; i++){
					if(item.logindate == dateArr[i]) {
						weekLoginUserArr[i] = item.count;
					}
				}
			});
			
			jsonDailySales.forEach((item, index) => {
				for(let i = 0; i < 7; i++){
					if(item.orderdate == dateArr[i]) {
						dailySalesArr[i] = item.sales;
					}
				}
			});
			
			jsonMonthlySales.forEach((item, index) => {
				month.push(item.month)
				monthlySalesArr.push(item.sales);
			});
			
			jsonYearSales.forEach((item, index) => {
				year.push(item.year)
				yearSalesArr.push(item.sales);
			});
			
			dateArr.reverse();
			
 			new Chart(
					document.getElementById("daily-order-chart"),
					{
						type : 'line',
						data : {
							labels : dateArr,
							datasets : [
									{
										data : dailySalesArr.reverse(),
										label : "일일 매출",
										borderColor : "#3e95cd",
										fill : false
									}
								 ]
						},
						options : {
							title : {
								display : true,
								text : '일주일간 일일 매출'
							}
						}
					});
			
			new Chart(
					document.getElementById("week-login-user-chart"),
					{
						type : 'line',
						data : {
							labels : dateArr,
							datasets : [
									{
										data : weekLoginUserArr.reverse(),
										label : "일일 사용자 접속",
										borderColor : "#3e95cd",
										fill : false
									}
								 ]
						},
						options : {
							title : {
								display : true,
								text : '일일 사용자 접속'
							}
						}
					});
			
			new Chart(
					document.getElementById("monthly-order-chart"),
					{
						type : 'line',
						data : {
							labels : month,
							datasets : [
									{
										data : monthlySalesArr,
										label : "월간 매출",
										borderColor : "#3e95cd",
										fill : false
									}
								 ]
						},
						options : {
							title : {
								display : true,
								text : '월간 매출'
							}
						}
					});
			
			new Chart(
					document.getElementById("year-order-chart"),
					{
						type : 'line',
						data : {
							labels : year,
							datasets : [
									{
										data : yearSalesArr,
										label : "연 매출",
										borderColor : "#3e95cd",
										fill : false
									}
								 ]
						},
						options : {
							title : {
								display : true,
								text : '연 매출'
							}
						}
					});
		
		</script>

	</div>

</body>

</html>