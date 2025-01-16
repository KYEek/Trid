<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<%-- BoardDTO --%>
<c:set var="boardDTO" value="${requestScope.boardDTO}" />

<%-- 관리자 질문 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 질문 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/board_detail.css">
</head>

<body>
	<%-- 질문 상세 --%>
	<div id="container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">

			<%-- 상세 정보 --%>
			<div id="item_container">
				<div id="detail_header">
					<h2>Q&A 조회 및 답변</h2>
					<button type = "button" class="button--ujarak" onclick="location.href='boardManage.trd'">돌아가기</button>
					<button type = "button" id="answer_button" class="button--ujarak">답변달기</button>
				</div>
			
				<div id="detail_container">		
					<span>${boardDTO.question_registerday}</span>
					<h3 class="value_span">question .no ${boardDTO.pk_question_no}</h3>
					<h1>${boardDTO.question_title}</h1>
					
					<span style="margin-top:20px;">질문</span>
					<p>${boardDTO.question_content}</p>
					
					<span>답변</span>
					<p>${boardDTO.question_answer}</p>
				</div>	
			</div>		
				
			<%-- 답변 저장 폼 --%>	
			<form id="answer_frm" name="answer_frm">
				<div id="answer_container">
					<%-- 질문 일련번호 --%>	
					<input type="hidden" name="questionNo" value="${boardDTO.pk_question_no}">
					
					<%-- 답변 textarea --%>	
					<textarea name="questionAnswer" id="questionAnswer"></textarea>
					<button id="submit_button" class="button--ujarak" type="button">제출</button>
				</div>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function() {
		
		// 답변 컨테이너 숨기기
		$("div#answer_container").hide();
		
		// 답변하기 버튼 클릭 시 답변 컨테이너 보이기
		$(document).on("click", "button#answer_button", function() {
			$("div#answer_container").show();
		});
	
		// 답변 제출 버튼 클릭
		$(document).on("click", "button#submit_button", function() {
			const answer = $("textarea#questionAnswer").val();
			
			// 답변 내용 유효성 검사
			if(answer == "" || answer.lenght > 100) {
				alert("올바른 답변을 입력하세요 (최대 100글자)");
				return false;
			}
			
			const frm = document.answer_frm;
			frm.action = "boardDetail.trd";
			frm.method = "post";
			frm.submit();
		});
		
	});
</script>

</body>

</html>