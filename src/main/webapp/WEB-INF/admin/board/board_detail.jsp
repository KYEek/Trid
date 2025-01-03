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
<link rel="stylesheet" href="${ctxPath}/css/admin/product_detail.css">

<%-- js --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script src="${ctxPath}/js/admin/product_detail.js"></script>
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
</head>

<body>
	<%-- BoardDTO --%>
	<c:set var="boardDTO" value="${requestScope.boardDTO}" />

	<%-- 상품 상세 --%>
	<div id="product_manage_container">

		<%-- 관리자 사이드 네비게이션 --%>
		<%@ include file="../side_navigation.jsp"%>

		<div id="main_container">

			<%-- 상세 정보 --%>
			<div id="detail_container">
			
			<div id="detail_header">
				<h2>Q&A 조회 및 답변</h2>
				<div>
					<%-- 돌아가기 버튼을 클릭 시 이전 페이지로 돌아간다. --%>
					<a id="return" href="boardManage.trd">돌아가기</a> 
					<button id="answer_button">답변달기</button>
				</div>
			</div>
				<div class="item"><span>질문 일련번호</span> <span>${boardDTO.pk_question_no}</span></div>
				<div class="item"><span>제목</span> <span>${boardDTO.question_title}</span></div>
				<div class="item"><span>내용</span> <span>${boardDTO.question_content}</span></div>
				<div class="item"><span>답변</span> <div>${boardDTO.question_answer}</div></div>
				
				<div id="answer_container">
					<form id="answer_frm" name="answer_frm">
						<input type="hidden" name="pkQuestionNo" value="${boardDTO.pk_question_no}">
						<textarea name="questionAnswer" id="questionAnswer"></textarea>
						<button id="submit_button" type="button">제출</button>
					</form>
				</div>
			</div>

		</div>
	</div>

</body>

</html>