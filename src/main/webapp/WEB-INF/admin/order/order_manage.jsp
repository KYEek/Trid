<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />
<%-- BoardDTO List --%>
<c:set var="orderList" value="${requestScope.orderList}" />

<%-- 질문 게시판 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trid Order Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/order_manage.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/util.js"></script>

</head>

<body>
	<div class="main_container">
	
		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp" %>
		
		<div class="content_container">
		
			<%-- 검색 카테고리 --%>
			<div class="search_container">
			<form id="sort_frm" name="sort_frm">
	
				<div class="manage_header">
					<h1 class="main_heading">Order Manage</h1>
				</div>
	
				<div class="manage_category">
					<div class="category_box">
					
						<div id="search_box">
							<select id="search_type" name="searchType">
								<option value="0">회원명</option>
								<option value="1">주문상품</option>
							</select>
							<input type="text" id="search_word" name="searchWord"/>
							
							<span>주문 상태</span>
							<select id="order_status" name="orderStatus">
								<option value="">전체</option>
								<option value="0">결제완료</option>
								<option value="1">상품준비</option>
								<option value="2">배송중</option>
								<option value="3">배송완료</option>
							</select>
						</div>
					
						<div class="range">
							<span>기간</span> 
							<input type="date" id="date_min_input" name="dateMin"/>
								&nbsp;~&nbsp;
							<input type="date" id="date_max_input" name="dateMax"/>
						</div>
					
					</div>
					
					<div class="sort_box">
						<select id="sort_select" name="sortCategory">
							<option value="0">최신순</option>
							<option value="1">오래된순</option>
						</select>

						<button type="button" class="button--ujarak" id="search_button">검색</button>
					</div>
				</div>
			</form>
		</div>
		
		<span>총 ${pagingDTO.totalRowCount}개 주문</span>
	
			<table class="table">
				<thead>
					<tr>
						<th scope="col">주문 일련번호</th>
						<th scope="col">회원번호</th>
						<th scope="col">회원명</th>
						<th scope="col">주문상품</th>
						<th scope="col">총 주문가격(&#8361;)</th>
						<th scope="col">주문상태</th>
						<th scope="col">주문일자</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty orderList}">
						<c:forEach items="${orderList}" var="orderDTO">
							<tr class="order_item">
								<td>${orderDTO.pkOrderNo}</td>
								<td>${orderDTO.memberDTO.pk_member_no}</td>
								<td>${orderDTO.memberDTO.member_name}</td>
								
								<td>${orderDTO.firstProductName}
									
									<c:if test="${orderDTO.totalProductDetailCount > 1}">
										외 ${orderDTO.totalProductDetailCount - 1}개 상품
									</c:if>
								
								</td>
								<td><fmt:formatNumber value="${orderDTO.orderTotalPrice}" pattern="#,###" /></td>
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
								
								<td>${orderDTO.orderDate}</td>
								
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<div>
				<%@ include file="../../paging.jsp" %>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			let oldSearchType = "${requestScope.searchType}"; // 검색어 타입 0:글제목 1:작성자명
			let oldSearchWord = "${requestScope.searchWord}"; // 검색어
			
			let oldSortCategory = "${requestScope.sortCategory}"; // 정렬 번호 0:최신순 1:오래된순
			
			let oldOrderStatus = "${requestScope.orderStatus}"; // 주문 상태 번호 0:결제완료 1:상품준비 2:배송준비 3:배송완료
			
			let oldDateMin = "${requestScope.dateMin}"; // 기존 최소 등록일
			let oldDateMax = "${requestScope.dateMax}"; // 기존 최대 등록일
			
			let url = "";
			
			keepSearchConditions();
			
			$(document).on("keydown", "input#search_word" , function(e) {
				if(e.keyCode == 13) {
					$("button#search_button").click();	
				}
			});
			
			// 검색버튼 클릭 시 정렬, 검색 조건이 포함되어 페이지 요청
			$(document).on("click","#search_button",function() {
				const frm = document.sort_frm;
				frm.action = 'orderManage.trd?curPage='+ ${paging.curPage};
				
				frm.searchWord.value = frm.searchWord.value.trim();  // 검색어 trim
				
				let dateMin = frm.dateMin.value; // 최소 등록일
				let dateMax = frm.dateMax.value; // 최대 등록일
				let orderStatus = frm.orderStatus.value; // 주문 상태
			
				// 최소 최대 날짜에 값이 들어있지만 유효한 날짜 형식이 아닌 경우
				if(!isBlank(dateMin) && !isBlank(dateMax)) {
					if(!(isValidDate(dateMin) && isValidDate(dateMax))) {
						alert("유효한 날짜 형식이 아닙니다.");
						frm.dateMax.value = "";
						frm.dateMax.value = "";
						return false;			
					}
				}
					
				// 두 날짜 값이 유효할 때 최소 날짜가 최대 날짜보다 이후인 경우
				if (isValidDate(dateMin) && isValidDate(dateMax)) {
					if(dateMin > dateMax){
						alert("시작일은 마지막일보다 앞서야 합니다.");
						frm.dateMin.value = "";
						frm.dateMax.value = "";
						return false;
					}
				}
				
				if (orderStatus.trim().length > 0 && !isValidNumber(orderStatus)) {
					alert("주문상태 선택값이 올바르지 않습니다.");
					return false;
				}

				frm.submit();
			});
			
			// 주문 리스트에서 요소 클릭 시 주문 상세로 이동
			$(document).on("click","tr.order_item", function(e) {
				const orderNo = $(this).find("td").eq(0).text();
				location.href = "orderDetail.trd?orderNo=" + orderNo;
			});
			
			// 페이징 시 기존 요청값 불러와 요소에 저장하는 함수
			function keepSearchConditions() {
				
				if (!isBlank(oldSearchType)) {
					$("select[name='searchType']").val(oldSearchType);
					url += "&searchType=" + oldSearchType;
				}
				
				if (!isBlank(oldSearchWord)) {
					$("input:text[name='searchWord']").val(oldSearchWord);
					url += "&searchWord=" + oldSearchWord;
				}
				
				if (!isBlank(oldSortCategory)) {
					$("select[name='sortCategory']").val(oldSortCategory);
					url += "&sortCategory=" + oldSortCategory;
				}

				if (!isBlank(oldOrderStatus)) {
					$("select[name='orderStatus']").val(oldOrderStatus);
					url += "&orderStatus=" + oldOrderStatus;
				}
				
				if (!isBlank(oldDateMin)) {
					$("input#date_min_input").val(oldDateMin);
					url += "&dateMin=" + oldDateMin;
				}
				
				if (!isBlank(oldDateMax)) {
					$("input#date_max_input").val(oldDateMax);
					url += "&dateMax=" + oldDateMax;
				}
			}
			
			$(document).on("click", "a.page_button", function () {
				const page = $(this).data("page");
	
				location.href = "orderManage.trd?curPage=" + page + url;
			});
		});
	
	</script>
</body>

</html>