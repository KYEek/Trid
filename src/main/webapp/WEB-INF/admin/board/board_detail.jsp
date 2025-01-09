<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- pageContextPath --%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<%-- 관리자 질문 상세 조회 페이지 --%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>관리자 질문 상세</title>

<%-- css --%>
<link rel="stylesheet" href="${ctxPath}/css/admin/board_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>

<body>
	<%-- BoardDTO --%>
	<c:set var="boardDTO" value="${requestScope.boardDTO}" />

	<%-- 질문 상세 --%>
	<div id="container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">

			<%-- 상세 정보 --%>
			<div id="item_container">
				<div id="detail_header">
					<h2>Q&A 조회 및 답변</h2>
					<button type = button class="button--ujarak" onclick="location.href='boardManage.trd'">돌아가기</button>
					<button id="answer_button" class="button--ujarak">답변달기</button>
				</div>
			
				<div id="detail_container">		
					<h3 class="value_span">question .no ${boardDTO.pk_question_no}</h3>
					<h1>${boardDTO.question_title}</h1>
					
					<span style="margin-top:20px;">질문</span>
					<p>${boardDTO.question_content}</p>
					
					<span>답변</span>
					<p>${boardDTO.question_answer}</p>
				</div>	
			</div>		
				
			<form id="answer_frm" name="answer_frm">
				<div id="answer_container">
					<input type="hidden" name="pkQuestionNo" value="${boardDTO.pk_question_no}">
					<textarea name="questionAnswer" id="questionAnswer"></textarea>
					<button id="submit_button" class="button--ujarak" type="button">제출</button>
				</div>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function() {
		
		$("div#answer_container").hide();
		
		$(document).on("click", "button#answer_button", function() {
			$("div#answer_container").show();
		});
	
		
		$(document).on("click", "button#submit_button", function() {
			const answer = $("textarea#questionAnswer").val();
			
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