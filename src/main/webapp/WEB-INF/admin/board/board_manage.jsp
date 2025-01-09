<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- 관리자 명 --%>
<c:set var="adminName" value="${sessionScope.adminName}" />
<%-- BoardDTO List --%>
<c:set var="boardList" value="${requestScope.boardList}" />

<%-- 질문 게시판 리스트 조회 페이지 --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trid Board Manage</title>

<%-- css --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/board_manage.css">

<%-- js --%>
<script src="${pageContext.request.contextPath}/js/admin/util.js"></script>
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
				<h1 class="main_heading">Q&A Manage</h1>
			</div>

			<div class="manage_category" style="align-items : flex-end;">
				<div class="board_search_box">
					<div>
						<div>
							<select id="search_type" name="searchType">
								<option value="0">제목</option>
								<option value="1">작성자명</option>
							</select>
							<input type="text" id="search_word" name="searchWord"/>
						</div>
						<div class="flexbox">
							<div>
								<span>답변상태</span>
								<select class="answer_select" name="answerStatus">
								    <option value="">전체</option>
								    <option value="0">답변대기</option>
								    <option value="1">답변완료</option>
								</select>
							</div> 
							<div>
								<span style="margin-left:20px;">비밀글 여부</span>
								<select class="private_select" name="privateStatus">
								    <option value="">전체</option>
								    <option value="0">공개글</option>
								    <option value="1">비밀글</option>
								</select>
							</div> 
							<div class="range">
								<span>기간</span> 
								<input type="date" id="date_min_input" name="dateMin"/>
									&nbsp;~&nbsp;
								<input type="date" id="date_max_input" name="dateMax"/>
							</div>
						</div>
					</div>
				</div>

				<div class="sort_box" style="margin-bottom:10px;">
					<select id="sort_select" name="sortCategory">
						<option value="0">최신순</option>
						<option value="1">오래된순</option>
					</select>

					<button type="button" class="button--ujarak" id="search_button">검색</button>
				</div>
			</div>
		</form>
	</div>
	
	<span>총 ${pagingDTO.totalRowCount}개 질문</span>

		<table class="table">
			<thead>
				<tr>
					<th scope="col">질문 일련번호</th>
					<th scope="col">회원 일련번호</th>
					<th scope="col">회원명</th>
					<th scope="col">제목</th>
					<th scope="col">비밀글여부</th>
					<th scope="col">답변상태</th>
					<th scope="col">등록일자</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty boardList}">
					<c:forEach items="${boardList}" var="boardDTO">
						<tr class="question_item">
							<td>${boardDTO.pk_question_no}</td>
							<td>${boardDTO.fk_member_no}</td>
							<td>${boardDTO.memberName}</td>
							<td>${boardDTO.question_title}</td>

							<c:choose>
								<c:when test="${boardDTO.question_isprivate == 0}">
									<td>공개</td>
								</c:when>
								<c:otherwise>
									<td>비공개</td>
								</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${boardDTO.question_status == 0}">
									<td>답변대기</td>
								</c:when>
								<c:otherwise>
									<td>답변완료</td>
								</c:otherwise>
							</c:choose>
							
							<td>${boardDTO.question_registerday.substring(0, 10)}</td>
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
		
		let oldPrivateStatus = "${requestScope.privateStatus}"; // 비밀글 여부 번호 0:공개글 1:비밀글 
		let oldAnswerStatus = "${requestScope.answerStatus}"; // 답변 여부 번호 0:답변대기 1:답변완료
		
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
			frm.action = 'boardManage.trd?curPage='+ ${paging.curPage};
			
			frm.searchWord.value = frm.searchWord.value.trim();  // 검색어 trim
			
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
		
		
		// 상품 리스트에서 요소 클릭 시 상품 상세로 이동
		$(document).on("click","tr.question_item", function(e) {
			const questionNo = $(this).find("td").eq(0).text();
			location.href = "boardDetail.trd?questionNo=" + questionNo;
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

			if (!isBlank(oldPrivateStatus)) {
				$("select[name='privateStatus']").val(oldPrivateStatus);
				url += "&privateStatus=" + oldPrivateStatus;
			}

			if (!isBlank(oldAnswerStatus)) {
				$("select[name='answerStatus']").val(oldAnswerStatus);
				url += "&answerStatus=" + oldAnswerStatus;
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
		
		// 페이징 처리 버튼 이벤트
		$(document).on("click", "a.page_button", function() {
			const page = $(this).data("page");

			location.href = "boardManage.trd?curPage=" + page + url;
		});
	});

</script>
</body>

</html>