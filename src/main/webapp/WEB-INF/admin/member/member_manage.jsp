<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />
<%-- MemberDTO List --%>
<c:set var="memberList" value="${requestScope.memberList}" />

<%-- 질문 게시판 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Trid Member Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/order/order_manage.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin/util.js"></script>

</head>

<body>
	<div class="main_container">
	
		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp" %>
		
		<div class="content_container">
		
			<%-- 검색 카테고리 --%>
			<div id="search_container">
			<form id="sort_frm" name="sort_frm">
	
				<div class="manage_header">
					<h1 class="main_heading">Member Manage</h1>
				</div>
	
				<div class="manage_category">
					<div class="category_box">
					
						<div id="search_box">
							<select id="search_type" name="searchType">
								<option value="0">회원명</option>
								<option value="1">이메일</option>
							</select>
							<input type="text" id="search_word" name="searchWord"/>
						</div>
						
						<label for="memberGender">성별</label>
						<select id="member_gender" name="memberGender">
							<option value="">전체</option>
							<option value="0">여성</option>
							<option value="1">남성</option>
						</select>
						
						<label for="memberIdle">휴무상태</label>
						<select id="member_idle" name="memberIdle">
							<option value="">전체</option>
							<option value="0">휴면</option>
							<option value="1">비휴면</option>
						</select>
						
						<label for="memberStatus">회원 상태</label>
						<select id="member_status" name="memberStatus">
							<option value="">전체</option>
							<option value="0">탈퇴</option>
							<option value="1">가입</option>
							<option value="2">정지</option>
						</select>
					
						<div class="range">
							<span>가입 기간</span> 
							<input type="date" id="date_min_input" name="dateMin"/>
								&nbsp;~&nbsp;
							<input type="date" id="date_max_input" name="dateMax"/>
						</div>
					
					</div>
					
					<div class="sort_box">
						<select id="sort_select" name="sortCategory">
							<option value="0">회원명 &#9650;</option>
							<option value="1">회원명 &#9660;</option>
							<option value="2">가입일 &#9650;</option>
							<option value="3">가입일 &#9660;</option>
						</select>

						<button type="button" id="search_button">검색</button>
					</div>
				</div>
			</form>
		</div>
		
		<span>총 ${pagingDTO.totalRowCount}명 회원</span>
	
			<table class="table">
				<thead>
					<tr>
						<th scope="col">회원 일련번호</th>
						<th scope="col">회원 이메일</th>
						<th scope="col">회원명</th>
						<th scope="col">성별</th>
						<th scope="col">생일</th>
						<th scope="col">상태</th>
						<th scope="col">휴면유무</th>
						<th scope="col">가입일자</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty memberList}">
						<c:forEach items="${memberList}" var="memberDTO">
							<tr class="member_item">
								<td>${memberDTO.pk_member_no}</td>
								<td>${memberDTO.member_email}</td>
								<td>${memberDTO.member_name}</td>
								<td>
									<c:if test="${memberDTO.member_gender == 0}" >여</c:if>
									<c:if test="${memberDTO.member_gender == 1}" >남</c:if>
								</td>
								
								<td>${memberDTO.member_birthday}</td>
								
								<td>
									<c:if test="${memberDTO.member_status == 0}" >탈퇴</c:if>
									<c:if test="${memberDTO.member_status == 1}" >가입</c:if>
									<c:if test="${memberDTO.member_status == 2}" >정지</c:if>
								</td>
								
								<td>
									<c:if test="${memberDTO.member_idle == 0}" >휴면</c:if>
									<c:if test="${memberDTO.member_idle == 1}" >활성화</c:if>
								</td>
								
								<td>${memberDTO.member_registerday}</td>
								
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
			let oldSearchType = "${requestScope.searchType}"; // 검색 타입 0: 회원명, 1: 이메일
			let oldSearchWord = "${requestScope.searchWord}"; // 검색어
			
			let oldSortCategory = "${requestScope.sortCategory}"; // 정렬 타입 0: 회원명 오름차순, 1: 회원명 내림차순, 2:가입일 오름차순, 3: 가입일 내림차순
			let oldMemberGender = "${requestScope.memberGender}"; // 회원 성별 0:여자, 1:남자
			let oldMemberIdle = "${requestScope.memberIdle}"; // 회원 휴면 상태 1 : 비휴면, 0 :휴면 (6개월 기준)
			let oldMemberStatus = "${requestScope.memberStatus}"; // 회원 상태 1 : 활성,  0: 탈퇴,  2: 정지
			
			let oldDateMin = "${requestScope.dateMin}"; // 기존 최소 등록일
			let oldDateMax = "${requestScope.dateMax}"; // 기존 최대 등록일
			
			let url = "";
			
			keepSearchConditions();
			
			// 검색버튼 클릭 시 정렬, 검색 조건이 포함되어 페이지 요청
			$(document).on("click","#search_button",function() {
				const frm = document.sort_frm;
				frm.action = 'memberManage.trd?curPage='+ ${paging.curPage};
				
				let dateMin = frm.dateMin.value; // 최소 등록일
				let dateMax = frm.dateMax.value; // 최대 등록일
			
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

				frm.submit();
			});
			
			// 주문 리스트에서 요소 클릭 시 주문 상세로 이동
			$(document).on("click","tr.member_item", function(e) {
				const memberNo = $(this).find("td").eq(0).text();
				location.href = "memberDetail.trd?memberNo=" + memberNo;
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

				if (!isBlank(oldMemberGender)) {
					$("select[name='memberGender']").val(oldMemberGender);
					url += "&memberGender=" + oldMemberGender;
				}
				
				if (!isBlank(oldMemberIdle)) {
					$("select[name='memberIdle']").val(oldMemberIdle);
					url += "&memberIdle=" + oldMemberIdle;
				}
				
				if (!isBlank(oldMemberStatus)) {
					$("select[name='memberStatus']").val(oldMemberStatus);
					url += "&memberStaus=" + oldMemberStatus;
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
	
				location.href = "memberManage.trd?curPage=" + page + url;
			});
		});
	
	</script>
</body>

</html>