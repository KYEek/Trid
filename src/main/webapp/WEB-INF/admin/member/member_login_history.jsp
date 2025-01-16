<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- LoginHistory Map List --%>
<c:set var="historyList" value="${requestScope.historyList}" />

<%-- 관리자 사용자 로그인 기록 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trid Member Login History</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/manage.css">

<%-- js --%>
<script type="text/javascript" src="${ctxPath}/js/admin/util.js"></script>

</head>

<body>
	<div class="main_container">
	
		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp" %>
		
		<div class="content_container">
		
			<%-- 검색 카테고리 --%>
			<div id="search_container">
			
				<div class="manage_header">
					<h2 style="margin-right:20px;">회원 로그인 이력</h2>
					<button type="button" class="button--ujarak" onclick="location.href='memberDetail.trd?memberNo=${historyList.get(0).memberNo}'">돌아가기</button>
				</div>
				
			</div>
		
			<span>총 ${pagingDTO.totalRowCount}번 접속</span>
	
			<%-- 사용자 로그인 기록 리스트 테이블 --%>
			<table class="table">
				<thead>
					<tr>
						<th scope="col">로그인 일련번호</th>
						<th scope="col">회원 일련번호</th>
						<th scope="col">이메일</th>
						<th scope="col">아이피</th>
						<th scope="col">로그인 일자</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty historyList}">
						<c:forEach items="${historyList}" var="historyMap">
							<tr class="member_item">
								<td>${historyMap.loginNo}</td>
								<td>${historyMap.memberNo}</td>
								<td>${historyMap.email}</td>
								<td>${historyMap.ip}</td>
								<td>${historyMap.loginDate}</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			
			<%-- 페이징 --%>
			<div>
				<%@ include file="../../paging.jsp" %>
			</div>
		</div>
	</div>
	
	<script>
	// 페이징 페이지 버튼 클릭 이벤트 함수
	$(document).on("click", "a.page_button", function () {
		const page = $(this).data("page");

		location.href = "memberLoginHistory.trd?memberNo=${requestScope.memberNo}&curPage=" + page;
	});
	
	</script>

</body>

</html>